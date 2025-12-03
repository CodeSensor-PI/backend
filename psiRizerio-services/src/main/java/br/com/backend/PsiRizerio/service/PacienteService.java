package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteKpiQtdInativosDTO;
import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteTokenDTO;
import br.com.backend.PsiRizerio.enums.StatusUsuario;
import br.com.backend.PsiRizerio.exception.*;
import br.com.backend.PsiRizerio.mapper.PacienteMapper;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.repositories.EnderecoRepository;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;

import br.com.backend.PsiRizerio.security.GerenciadorTokenJwt;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PacienteService {

    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;
    private final PacienteMapper pacienteMapper;
    private final EnderecoRepository enderecoRepository;
    private final RabbitTemplate rabbitTemplate;
    private final S3Service s3Service; // Opcional
    private final StringRedisTemplate redis;


    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*()-_=+";

    @Autowired
    public PacienteService(
            PacienteRepository pacienteRepository,
            PasswordEncoder passwordEncoder,
            GerenciadorTokenJwt gerenciadorTokenJwt,
            AuthenticationManager authenticationManager,
            PacienteMapper pacienteMapper,
            EnderecoRepository enderecoRepository,
            StringRedisTemplate redis,
            RabbitTemplate rabbitTemplate,
            @Autowired(required = false) S3Service s3Service) {
        this.pacienteRepository = pacienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.authenticationManager = authenticationManager;
        this.pacienteMapper = pacienteMapper;
        this.enderecoRepository = enderecoRepository;
        this.redis = redis;
        this.rabbitTemplate = rabbitTemplate;
        this.s3Service = s3Service;
    }


    @Value("${app.rabbitmq.exchange:email-exchange}")
    private String emailExchange;

    @Value("${app.rabbitmq.routing-key:email.send}")
    private String emailRoutingKey;

    private static final int MAX_TENTATIVAS = 5;
    private static final long BLOQUEIO_SEGUNDOS = 60;

    public Paciente createUser(Paciente paciente) {
        if (pacienteRepository.existsByEmailIgnoreCase(paciente.getEmail())
                && paciente.getStatus() == StatusUsuario.ATIVO) throw new EntidadeConflitoException();

        if (!isValidEmail(paciente.getEmail())) throw new EntidadeInvalidaException();

        String senha = gerarSenha(12);
        String senhaCriptografada = passwordEncoder.encode(senha);
        paciente.setSenha(senhaCriptografada);

        paciente.setCreatedAt(LocalDateTime.now());
        Paciente salvo = pacienteRepository.save(paciente);

        try {
            String nome = salvo.getNome() != null ? salvo.getNome() : "Paciente";
            String email = salvo.getEmail();
            String subject = "Acesso ao sistema de agendamento de consultas";
            String body = String.format("Olá, %s!\n\n" +
                    "Seu acesso ao sistema de Agendamento de Consultas Psicológicas foi criado com sucesso." +
                    "\n\nPara realizar seu primeiro login, utilize as seguintes credenciais:\n " +
                    "\uD83D\uDD11 Usuário: %s\n" +
                    "\uD83D\uDD12 Senha temporária: %s\n\n" +
                    "Por motivos de segurança, será solicitado que você altere essa senha logo no primeiro acesso." +
                    "Recomendamos que escolha uma senha forte, contendo letras maiúsculas, minúsculas, números e símbolos.\n\n" +
                    "Se você não solicitou este cadastro, por favor desconsidere este e-mail ou entre em contato com nossa equipe de suporte.\n\n" +
                    "Atenciosamente,\n" +
                    "Equipe de Suporte – Psicóloga Jéssica Rizerio", nome, email, senha);
            Map<String, Object> payload = Map.of(
                    "to", salvo.getEmail(),
                    "subject", subject,
                    "body", body
            );
            rabbitTemplate.convertAndSend(emailExchange, emailRoutingKey, payload);
            log.info("Mensagem de e-mail enfileirada para {}", salvo.getEmail());
        } catch (Exception e) {
            log.warn("Falha ao publicar e-mail de senha temporária para {}", paciente.getEmail(), e);
        }

        return salvo;
    }

    public Paciente update(Integer id, Paciente paciente) {
        Paciente pacienteToUpdate = pacienteRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        if (paciente.getCpf() != null && pacienteRepository.existsByCpfIgnoreCaseAndIdNot(paciente.getCpf(), id)) {
            throw new EntidadeConflitoException();
        }

        if (paciente.getEmail() != null) {
            if (pacienteRepository.existsByEmailIgnoreCaseAndIdNot(paciente.getEmail(), id)) {
                throw new EntidadeConflitoException();
            }
            if (!isValidEmail(paciente.getEmail())) {
                throw new EntidadeInvalidaException();
            }
            pacienteToUpdate.setEmail(paciente.getEmail());
        }

        if (paciente.getNome() != null) {
            pacienteToUpdate.setNome(paciente.getNome());
        }

        if (paciente.getCpf() != null) {
            pacienteToUpdate.setCpf(paciente.getCpf());
        }

        if (paciente.getFkEndereco() != null && paciente.getFkEndereco().getId() != null) {
            Integer enderecoId = paciente.getFkEndereco().getId();
            Endereco endereco = enderecoRepository.findById(enderecoId)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Endereço não encontrado com ID: " + enderecoId));
            pacienteToUpdate.setFkEndereco(endereco);
        } else {
            pacienteToUpdate.setFkEndereco(null);
        }

        if (paciente.getFkPlano() != null && paciente.getFkPlano().getId() != null) {
            pacienteToUpdate.setFkPlano(paciente.getFkPlano());
        }

        // Directly update the status field
        pacienteToUpdate.setStatus(paciente.getStatus());

        pacienteToUpdate.setUpdatedAt(LocalDateTime.now());

        return pacienteRepository.save(pacienteToUpdate);
    }

    public Paciente findById(Integer id) {
        if (id == null) throw new EntidadeInvalidaException();

        Paciente paciente = pacienteRepository.findById(id).orElseThrow((EntidadeNaoEncontradaException::new));

        return paciente;
    }

    public Paciente desativarPaciente(Integer id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        paciente.setStatus(StatusUsuario.INATIVO);
        paciente.setUpdatedAt(LocalDateTime.now());
        return pacienteRepository.save(paciente);
    }

    public Paciente ativarPaciente(Integer id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        paciente.setStatus(StatusUsuario.ATIVO);
        paciente.setUpdatedAt(LocalDateTime.now());

        return pacienteRepository.save(paciente);
    }

    public Page<Paciente> findAll(Pageable pageable) {
        return pacienteRepository.findAll(pageable);
    }

    public PacienteTokenDTO autenticar(Paciente paciente) {

        final String email = paciente.getEmail();
        final String key = "login:tentativas:" + email;

        String valor = redis.opsForValue().get(key);
        int tentativas = valor != null ? Integer.parseInt(valor) : 0;

        if (tentativas >= MAX_TENTATIVAS) {
            Long timeToLiveRestante = redis.getExpire(key);
            throw new MuitasRequisicoesException(
                    "Muitas tentativas. Aguarde " + timeToLiveRestante + " segundos.",
                    timeToLiveRestante
            );
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, paciente.getSenha())
            );

            redis.delete(key);

            Paciente pacienteAutenticado = pacienteRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email não encontrado"));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = gerenciadorTokenJwt.generateToken(authentication);

            return pacienteMapper.toDtoToken(pacienteAutenticado, token);

        } catch (Exception e) {

            redis.opsForValue().set(
                    key,
                    String.valueOf(tentativas + 1),
                    BLOQUEIO_SEGUNDOS,
                    TimeUnit.SECONDS
            );

            int restantes = MAX_TENTATIVAS - (tentativas + 1);

            if (restantes <= 0) {
                throw new MuitasRequisicoesException(
                        "Conta bloqueada por " + BLOQUEIO_SEGUNDOS + " segundos.",
                        (long) BLOQUEIO_SEGUNDOS
                );
            }

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciais inválidas. Tentativas restantes: " + restantes
            );
        }
    }

    public void updateSenha(Integer id, String senhaAtual, String novaSenha) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        if (!passwordEncoder.matches(senhaAtual, paciente.getSenha())) {
            throw new EntidadePrecondicaoFalhaException();
        }

        if (senhaAtual.equals(novaSenha)) {
            throw new EntidadeConflitoException();
        }

        String novaSenhaCripto = passwordEncoder.encode(novaSenha);

        paciente.setSenha(novaSenhaCripto);
        pacienteRepository.save(paciente);
    }

    public Paciente addDadosPrimeiroLogin(Integer id, Paciente paciente) {
        Paciente pacienteToUpdate = pacienteRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));

        if (pacienteRepository.existsByCpf(paciente.getCpf())) throw new EntidadeConflitoException();

        if (paciente.getFkEndereco() == null && paciente.getFkEndereco().getId() == null)
            throw new EntidadeInvalidaException();

        pacienteToUpdate.setDataNasc(paciente.getDataNasc());
        pacienteToUpdate.setFkEndereco(paciente.getFkEndereco());
        pacienteToUpdate.setCpf(paciente.getCpf());
        pacienteToUpdate.setMotivoConsulta(paciente.getMotivoConsulta());
        pacienteToUpdate.setUpdatedAt(LocalDateTime.now());

        return pacienteRepository.save(pacienteToUpdate);
    }

    public PacienteKpiQtdInativosDTO getQtdInativosKpi() {
        Double percentual = pacienteRepository.getPercentualInativos(StatusUsuario.INATIVO.name());
        return new PacienteKpiQtdInativosDTO(percentual);
    }

    public List<Paciente> buscarPorNome(String nome) {
        return pacienteRepository.findByNomeStartingWithIgnoreCase(nome);
    }

    public boolean cpfExiste(String cpf) {
        return pacienteRepository.existsByCpf(cpf);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static String gerarSenha(int tamanho) {
        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder(tamanho);

        for (int i = 0; i < tamanho; i++) {
            int index = random.nextInt(CARACTERES.length());
            senha.append(CARACTERES.charAt(index));
        }

        return senha.toString();
    }

    public Paciente uploadImagemPaciente(Integer id, MultipartFile file) {
        // Verifica se o S3Service está disponível
        if (s3Service == null) {
            log.error("Tentativa de upload de imagem mas S3Service não está configurado. Configure aws.s3.enabled=true e as credenciais AWS no .env");
            throw new RuntimeException("Upload de imagem não está disponível. Configure as credenciais AWS no arquivo .env");
        }

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        try {
            // Deleta imagem antiga se existir
            if (paciente.getImagemUrl() != null && !paciente.getImagemUrl().isEmpty()) {
                s3Service.deleteImage(paciente.getImagemUrl());
            }

            // Faz upload da nova imagem
            String imageUrl = s3Service.uploadImage(file, "pacientes");
            paciente.setImagemUrl(imageUrl);
            paciente.setUpdatedAt(LocalDateTime.now());

            return pacienteRepository.save(paciente);

        } catch (Exception e) {
            log.error("Erro ao fazer upload da imagem do paciente {}", id, e);
            throw new RuntimeException("Erro ao fazer upload da imagem: " + e.getMessage());
        }
    }

}
