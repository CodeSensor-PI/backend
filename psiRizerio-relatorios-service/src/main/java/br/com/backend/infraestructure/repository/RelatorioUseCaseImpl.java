package br.com.backend.infraestructure.repository;

import br.com.backend.domain.entity.Relatorio;
import br.com.backend.domain.usecase.RelatorioUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("relatorioUseCase") // Nome explícito para evitar conflitos
public class RelatorioUseCaseImpl implements RelatorioUseCase {

    private final RelatorioRepository repository;

    public RelatorioUseCaseImpl(RelatorioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Relatorio> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Relatorio salvar(Relatorio relatorio) {
        Optional<Relatorio> existente = Optional.ofNullable(repository.findByFkSessao(relatorio.getFkSessao()));

        if (existente.isPresent()) {
            throw new IllegalStateException("Já existe um relatório para esta sessão.");
        }

        return repository.save(relatorio);
    }

    @Override
    public Relatorio buscarPorSessao(Integer fkSessao) {
        return repository.findByFkSessao(fkSessao);
    }

    @Override
    public List<Relatorio> buscarPorPaciente(Integer fkPaciente) {
        return repository.findByFkPaciente(fkPaciente);
    }

    @Override
    public Relatorio atualizar(UUID id, Relatorio dadosAtualizados) {
        Relatorio existente = (Relatorio) repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relatório não encontrado"));

        existente.setConteudo(dadosAtualizados.getConteudo());
        existente.setDataAtualizacao(LocalDateTime.now());

        return repository.save(existente);
    }


    @Override
    @Transactional
    public void excluir(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Relatório com ID " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void excluirPorSessao(Integer fkSessao) {
        Relatorio relatorio = repository.findByFkSessao(fkSessao);
        if (relatorio == null) {
            throw new EntityNotFoundException("Nenhum relatório encontrado para a sessão " + fkSessao);
        }
        repository.delete(relatorio);
    }



}
