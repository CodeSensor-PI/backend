package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.persistence.entities.Telefone;
import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import br.com.backend.PsiRizerio.persistence.repositories.TelefoneRepository;
import br.com.backend.PsiRizerio.persistence.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;
    private final UsuarioRepository usuarioRepository;

    public TelefoneService(TelefoneRepository telefoneRepository, UsuarioRepository usuarioRepository) {
        this.telefoneRepository = telefoneRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Telefone createTelefone(Telefone telefone) {
        telefone.setFkCliente(usuarioRepository.findById(telefone.getFkCliente().getId())
                .orElseThrow((EntidadeNaoEncontradaException::new)));
        telefone.setCreatedAt(LocalDateTime.now());
        return telefoneRepository.save(telefone);
    }

    public Telefone update(Integer id, Telefone telefone) {
        Telefone telefoneToUpdate = telefoneRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));

        telefoneToUpdate.setDdd(telefone.getDdd());
        telefoneToUpdate.setNumero(telefone.getNumero());
        telefoneToUpdate.setTipo(telefone.getTipo());
        telefoneToUpdate.setUpdatedAt(LocalDateTime.now());
        return telefoneRepository.save(telefoneToUpdate);
    }

    public Telefone findById(Integer id) {
        return telefoneRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));
    }

    public List<Telefone> findByFkCliente(Integer id) {
        List<Telefone> telefones = telefoneRepository.findByFkCliente(id);
        if (telefones.isEmpty()) throw new EntidadeNaoEncontradaException();
        return telefones;
    }

    public List<Telefone> findAll() {
        if (telefoneRepository.findAll().isEmpty()) throw new EntidadeNaoEncontradaException();
        return telefoneRepository.findAll();
    }

    public void delete(Integer id) {
        Telefone telefone = telefoneRepository.findById(id)
                .orElseThrow((EntidadeNaoEncontradaException::new));
        telefoneRepository.delete(telefone);
    }
}
