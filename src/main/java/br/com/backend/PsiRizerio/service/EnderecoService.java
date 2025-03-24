package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.EnderecoDTO;
import br.com.backend.PsiRizerio.exception.EntidadeInvalidaException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadeSemConteudoException;
import br.com.backend.PsiRizerio.mapper.EnderecoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import br.com.backend.PsiRizerio.persistence.repositories.EnderecoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnderecoService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    public EnderecoDTO createEndereco(EnderecoDTO enderecoDTO) {
        enderecoDTO.setCreatedAt(LocalDateTime.now());
        enderecoDTO.setUpdatedAt(LocalDateTime.now());
        Endereco enderecoToMapper = enderecoMapper.toEntity(enderecoDTO);
        Endereco enderecoToSave = enderecoRepository.save(enderecoToMapper);
        return enderecoMapper.toDto(enderecoToSave);
    }

    public EnderecoDTO update(Integer id, EnderecoDTO enderecoDTO) {
        if (id == null) throw new EntidadeInvalidaException();

        Endereco enderecoToUpdate = enderecoRepository.findById(id).orElseThrow(EntidadeInvalidaException::new);
        enderecoToUpdate.setCep(enderecoDTO.getCep());
        enderecoToUpdate.setCidade(enderecoDTO.getCidade());
        enderecoToUpdate.setLogradouro(enderecoDTO.getLogradouro());
        enderecoToUpdate.setBairro(enderecoDTO.getBairro());
        enderecoToUpdate.setNumero(enderecoDTO.getNumero());
        enderecoToUpdate.setUf(enderecoDTO.getUf());
        enderecoToUpdate.setUpdatedAt(LocalDateTime.now());
        return enderecoMapper.toDto(enderecoRepository.save(enderecoToUpdate));
    }

    public void delete(Integer id) {
        if (id == null) throw new EntidadeInvalidaException();

        if (!enderecoRepository.existsById(id)) throw new EntidadeNaoEncontradaException();

        enderecoRepository.deleteById(id);
    }

    public EnderecoDTO findById(Integer id) {
        if (id == null) throw new EntidadeInvalidaException();

        Endereco endereco = enderecoRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);

        return enderecoMapper.toDto(endereco);
    }

    public List<EnderecoDTO> findAll() {
        List<Endereco> enderecos = enderecoRepository.findAll().stream().toList();
        if (enderecos.isEmpty()) throw new EntidadeSemConteudoException();
        return enderecoMapper.toDto(enderecos);
    }
}
