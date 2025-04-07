package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.EntidadeConflitoException;
import br.com.backend.PsiRizerio.exception.EntidadeInvalidaException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadeSemConteudoException;
import br.com.backend.PsiRizerio.mapper.EnderecoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import br.com.backend.PsiRizerio.persistence.repositories.EnderecoRepository;
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

    public Endereco createEndereco(Endereco endereco) {
        if (enderecoRepository.existsByCepAndBairroAndNumeroAndLogradouroAndUfIgnoreCase(
                endereco.getCep(), endereco.getBairro(), endereco.getNumero(), endereco.getLogradouro(), endereco.getUf())) {
            throw new EntidadeConflitoException();
        }

        endereco.setCreatedAt(LocalDateTime.now());
        return enderecoRepository.save(endereco);
    }

    public Endereco update(Integer id, Endereco endereco) {
        Endereco enderecoToUpdate = enderecoRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);

        endereco.setId(id);
        enderecoToUpdate.setCep(endereco.getCep());
        enderecoToUpdate.setBairro(endereco.getBairro());
        enderecoToUpdate.setNumero(endereco.getNumero());
        enderecoToUpdate.setLogradouro(endereco.getLogradouro());
        enderecoToUpdate.setUf(endereco.getUf());
        enderecoToUpdate.setUpdatedAt(LocalDateTime.now());
        return enderecoRepository.save(enderecoToUpdate);
    }

    public Endereco findById(Integer id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(EntidadeNaoEncontradaException::new);
        return endereco;
    }

    public List<Endereco> findAll() {
        List<Endereco> enderecos = enderecoRepository.findAll();

        if (enderecos.isEmpty()) throw new EntidadeSemConteudoException();

        return enderecos;
    }

    public void delete(Integer id) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(EntidadeNaoEncontradaException::new);
        enderecoRepository.deleteById(id);
    }
}
