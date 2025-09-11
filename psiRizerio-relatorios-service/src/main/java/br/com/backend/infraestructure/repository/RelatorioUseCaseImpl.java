package br.com.backend.infraestructure.repository;

import br.com.backend.domain.entity.Relatorio;
import br.com.backend.domain.usecase.RelatorioUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RelatorioUseCaseImpl implements RelatorioUseCase {

    public final RelatorioRepository repository;

    public RelatorioUseCaseImpl(RelatorioRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Relatorio> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Relatorio salvar(Relatorio relatorio) {
        return repository.save(relatorio);
    }

    @Override
    public Relatorio buscarPorSessao(Integer fkSessao) {
        return null;
    }

    @Override
    public List<Relatorio> buscarPorPaciente(Integer fkPaciente) {
        return repository.findByFkPaciente(fkPaciente);
    }

}
