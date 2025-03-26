package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    void deleteById(Integer id);

    List<Endereco> findAll();

    Endereco save(Endereco endereco);

    boolean existsByCepAndBairroAndNumeroAndLogradouroAndUfIgnoreCase(String cep, String bairro, String numero, String logradouro, String uf);
}
