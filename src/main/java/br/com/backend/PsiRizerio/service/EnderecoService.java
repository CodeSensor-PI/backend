package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.dto.EnderecoDTO;
import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoCreateDTO;
import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoResponseDTO;
import br.com.backend.PsiRizerio.dto.enderecoDTO.EnderecoUpdateDTO;
import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
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

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    public EnderecoCreateDTO createEndereco(EnderecoCreateDTO enderecoDTO) {
        if (enderecoRepository.existsByCepAndBairroAndNumeroAndLogradouroAndUfIgnoreCase(enderecoDTO.getCep(),
                enderecoDTO.getBairro(), enderecoDTO.getNumero(), enderecoDTO.getLogradouro(), enderecoDTO.getUf())){
            throw new EntidadeConflitoException();
        }

        enderecoDTO.setCreatedAt(LocalDateTime.now());
        Endereco enderecoToMapper = enderecoMapper.toEntity(enderecoDTO);
        Endereco enderecoToSave = enderecoRepository.save(enderecoToMapper);
        return enderecoMapper.toDto(enderecoToSave);
    }

    public EnderecoUpdateDTO update(Integer id, EnderecoUpdateDTO enderecoDTO) {
        Endereco enderecoToUpdate = enderecoRepository.findById(id)
                .orElseThrow(EntidadeInvalidaException::new);

        enderecoToUpdate.setCep(enderecoDTO.getCep());
        enderecoToUpdate.setCidade(enderecoDTO.getCidade());
        enderecoToUpdate.setLogradouro(enderecoDTO.getLogradouro());
        enderecoToUpdate.setBairro(enderecoDTO.getBairro());
        enderecoToUpdate.setNumero(enderecoDTO.getNumero());
        enderecoToUpdate.setUf(enderecoDTO.getUf());
        enderecoToUpdate.setUpdatedAt(LocalDateTime.now());

        return enderecoMapper.toDtoUpdate(enderecoRepository.save(enderecoToUpdate));
    }

    public void delete(Integer id) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);
        enderecoRepository.deleteById(id);
    }

    public EnderecoResponseDTO findById(Integer id) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);
        return enderecoMapper.toDtoResponse(endereco);
    }

    public List<EnderecoResponseDTO> findAll() {
        List<Endereco> enderecos = enderecoRepository.findAll();

        if (enderecos.isEmpty()) throw new EntidadeSemConteudoException();

        return enderecoMapper.toDtoList(enderecos);
    }
}
