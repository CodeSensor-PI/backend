package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.pacienteDTO.PacienteDetalhesDTO;
import br.com.backend.PsiRizerio.dto.psicologoDTO.PsicologoDetalhesDTO;
import br.com.backend.PsiRizerio.persistence.entities.Paciente;
import br.com.backend.PsiRizerio.persistence.entities.Psicologo;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;
import br.com.backend.PsiRizerio.persistence.repositories.PsicologoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {

  @Autowired
  private PacienteRepository pacienteRepository;

  @Autowired
  private PsicologoRepository psicologoRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Paciente> pacienteOpt = pacienteRepository.findByEmail(username);
    if (pacienteOpt.isPresent()) {
      return new PacienteDetalhesDTO(pacienteOpt.get());
    }

    Optional<Psicologo> psicologoOpt = psicologoRepository.findByEmail(username);
    if (psicologoOpt.isPresent()) {
      Psicologo psicologo = psicologoOpt.get();
      return PsicologoDetalhesDTO.builder()
              .nome(psicologo.getNome())
              .email(psicologo.getEmail())
              .senha(psicologo.getSenha())
              .build();
    }

    throw new UsernameNotFoundException(String.format("Usuário com email %s não encontrado", username));
  }
}
