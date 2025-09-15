package br.com.backend.domain.usecase;

import br.com.backend.domain.entity.Relatorio;
import java.util.List;
import java.util.UUID;

public interface RelatorioUseCase {
    List<Relatorio> listarTodos();
    Relatorio salvar(Relatorio relatorio);
    Relatorio buscarPorSessao(Integer fkSessao);
    List<Relatorio> buscarPorPaciente(Integer fkPaciente);
    Relatorio atualizar(UUID id, Relatorio relatorio);
    void excluir(UUID id);
    void excluirPorSessao(Integer fkSessao);
}