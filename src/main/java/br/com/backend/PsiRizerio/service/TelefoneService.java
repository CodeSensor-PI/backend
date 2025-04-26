package br.com.backend.PsiRizerio.service;

import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.persistence.entities.Telefone;
import br.com.backend.PsiRizerio.persistence.repositories.TelefoneRepository;
import br.com.backend.PsiRizerio.persistence.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;
    private final PacienteRepository pacienteRepository;

    public TelefoneService(TelefoneRepository telefoneRepository, PacienteRepository pacienteRepository) {
        this.telefoneRepository = telefoneRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public Telefone createTelefone(Telefone telefone) {
        telefone.setFkPaciente(pacienteRepository.findById(telefone.getFkPaciente().getId())
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

    public List<Telefone> findByFkPaciente(Integer id) {
        List<Telefone> telefones = telefoneRepository.findByFkPaciente(id);
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
