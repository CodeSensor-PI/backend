package br.com.backend.infraestructure.repository;

import br.com.backend.domain.entity.Relatorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
    Relatorio findByFkSessao(Integer fkSessao);
    List<Relatorio> findByFkPaciente(Integer fkPaciente);
    Page<Relatorio> findByFkPaciente(Integer fkPaciente, Pageable pageable);
    Optional<Object> findById(UUID id);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
