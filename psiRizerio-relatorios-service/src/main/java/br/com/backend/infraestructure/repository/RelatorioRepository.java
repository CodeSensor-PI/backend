package br.com.backend.infraestructure.repository;

import br.com.backend.domain.entity.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
    Relatorio findByFkSessao(Integer fkSessao);
    List<Relatorio> findByFkPaciente(Integer fkPaciente);
}
