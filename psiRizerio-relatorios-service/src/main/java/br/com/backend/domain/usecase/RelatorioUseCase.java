package br.com.backend.domain.usecase;

import br.com.backend.domain.entity.Relatorio;
import java.util.List;

public interface RelatorioUseCase {
    List<Relatorio> listarTodos();
    Relatorio salvar(Relatorio relatorio);
    Relatorio buscarPorSessao(Integer fkSessao);
    List<Relatorio> buscarPorPaciente(Integer fkPaciente);
}