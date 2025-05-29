package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    void deleteById(Integer id);

    List<Endereco> findAll();

    Endereco save(Endereco endereco);

    @Query("""
    SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
    FROM Endereco e
    WHERE 
        e.cep = :cep AND
        LOWER(e.logradouro) = LOWER(:logradouro) AND
        e.numero = :numero AND
        LOWER(e.cidade) = LOWER(:cidade) AND
        LOWER(e.uf) = LOWER(:uf) AND
        LOWER(e.bairro) = LOWER(:bairro)
    """)
    boolean findEndereco(
            @Param("cep") String cep,
            @Param("logradouro") String logradouro,
            @Param("numero") String numero,
            @Param("cidade") String cidade,
            @Param("uf") String uf,
            @Param("bairro") String bairro
    );

    Optional<Endereco> findByCepAndNumero(String cep, String numero);
}
