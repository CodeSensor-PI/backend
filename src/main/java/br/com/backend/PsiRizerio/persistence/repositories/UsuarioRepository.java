package br.com.backend.PsiRizerio.persistence.repositories;

import br.com.backend.PsiRizerio.mapper.UsuarioMapper;
import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario save(Usuario usuario);

    void deleteById(Integer id);
    Optional<Usuario> findById(Integer id);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);

    boolean existsByEmailOrCpfIgnoreCase(String email, String cpf);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Integer id);

    boolean existsByCpfIgnoreCaseAndIdNot(String cpf, Integer id);

    boolean existsByCpfOrEmailAndIdNot(String cpf, String email, Integer id);

    boolean existsByEmail(String email);

}
