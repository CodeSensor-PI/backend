package br.com.backend.PsiRizerio.controllerTest;

import br.com.backend.PsiRizerio.persistence.entities.User;
import br.com.backend.PsiRizerio.persistence.repositories.UserRepository;
import br.com.backend.PsiRizerio.service.UserService;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserControllerTests {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void createUserTest() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setName("Teste");
        user.setEmail("teste");
        user.setPassword("teste");
        user.setPhone("teste");
        user.setAddress("teste");
        user.setCpf("teste");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());

        userService.createUser(user);
    }

    @Test
    void updateUserTest() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Teste");
        user.setEmail("teste");
        user.setPassword("teste");
        user.setPhone("teste");
        user.setAddress("teste");
        user.setCpf("teste");

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(new User()));
    }

    @Test
    void findByIdTest() throws Exception {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(new User()));
    }

    @Test
    void deleteTest() throws Exception {
        Mockito.when(userRepository.existsById(Mockito.anyLong())).thenReturn(true);
    }
}
