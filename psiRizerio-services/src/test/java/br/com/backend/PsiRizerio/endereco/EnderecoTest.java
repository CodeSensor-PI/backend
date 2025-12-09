package br.com.backend.PsiRizerio.endereco;

import br.com.backend.PsiRizerio.exception.EntidadeInvalidaException;
import br.com.backend.PsiRizerio.exception.EntidadeNaoEncontradaException;
import br.com.backend.PsiRizerio.exception.EntidadeSemConteudoException;
import br.com.backend.PsiRizerio.persistence.entities.Endereco;
import br.com.backend.PsiRizerio.persistence.repositories.EnderecoRepository;
import br.com.backend.PsiRizerio.service.EnderecoService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnderecoTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Nested
    class CreateEnderecoTests {
        @Test
        public void createEnderecoSuccessfully() {
            Endereco endereco = new Endereco();
            when(enderecoRepository.save(endereco)).thenReturn(endereco);
            Endereco createdEndereco = enderecoService.createEndereco(endereco);
            assertNotNull(createdEndereco);
            verify(enderecoRepository, times(1)).save(endereco);
        }

    }

    @Nested
    class FindEnderecoTests {
        @Test
        public void findAllEnderecosSuccessfully() {
            Endereco endereco = new Endereco();
            List<Endereco> enderecos = Collections.singletonList(endereco);
            when(enderecoRepository.findAll()).thenReturn(enderecos);
            List<Endereco> foundEnderecos = enderecoService.findAll();
            assertEquals(1, foundEnderecos.size());
            assertEquals(endereco, foundEnderecos.get(0));
        }

        @Test
        public void findAllEnderecosEmptyList() {
            when(enderecoRepository.findAll()).thenReturn(Collections.emptyList());
            assertThrows(EntidadeSemConteudoException.class, () -> enderecoService.findAll());
        }

        @Test
        public void findEnderecoByIdSuccessfully() {
            Integer id = 1;
            Endereco endereco = new Endereco();
            when(enderecoRepository.findById(id)).thenReturn(Optional.of(endereco));
            Endereco foundEndereco = enderecoService.findById(id);
            assertNotNull(foundEndereco);
            assertEquals(endereco, foundEndereco);
        }

        @Test
        public void findEnderecoByIdNotFound() {
            Integer id = 1;
            when(enderecoRepository.findById(id)).thenReturn(Optional.empty());
            assertThrows(EntidadeNaoEncontradaException.class, () -> enderecoService.findById(id));
        }
    }

    @Nested
    class UpdateEnderecoTests {
        @Test
        public void updateEnderecoSuccessfully() {
            Integer id = 1;
            Endereco endereco = new Endereco();
            when(enderecoRepository.findById(id)).thenReturn(Optional.of(endereco));
            when(enderecoRepository.save(endereco)).thenReturn(endereco);
            Endereco updatedEndereco = enderecoService.update(id, endereco);
            assertNotNull(updatedEndereco);
            verify(enderecoRepository, times(1)).save(endereco);
        }

        @Test
        public void updateEnderecoNotFound() {
            Integer id = 1;
            Endereco endereco = new Endereco();
            when(enderecoRepository.findById(id)).thenReturn(Optional.empty());
            assertThrows(EntidadeNaoEncontradaException.class, () -> enderecoService.update(id, endereco));
        }
    }

    @Nested
    class DeleteEnderecoTests {
        @Test
        public void deleteEnderecoSuccessfully() {
            Integer id = 1;
            Endereco endereco = new Endereco();
            when(enderecoRepository.findById(id)).thenReturn(Optional.of(endereco));
            doNothing().when(enderecoRepository).deleteById(id);
            enderecoService.delete(id);
            verify(enderecoRepository, times(1)).deleteById(id);
        }

        @Test
        public void deleteEnderecoNotFound() {
            Integer id = 1;
            when(enderecoRepository.findById(id)).thenReturn(Optional.empty());
            assertThrows(EntidadeNaoEncontradaException.class, () -> enderecoService.delete(id));
        }
    }
}
