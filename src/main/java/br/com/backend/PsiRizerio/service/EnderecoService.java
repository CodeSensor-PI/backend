package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadeSemConteudoException;
import br.com.backend.PsiRizerio.mapper.EnderecoMapper;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import br.com.backend.PsiRizerio.persistence.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;


    public Endereco createEndereco(Endereco endereco) {

        boolean enderecoExiste = enderecoRepository.findEndereco(
                endereco.getCep(), endereco.getBairro(), endereco.getNumero(), endereco.getLogradouro(), endereco.getUf(), endereco.getCidade());

        if (enderecoExiste) {
            return findById(endereco.getId());
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
