package br.com.backend.PsiRizerio.autenticacao;

import br.com.backend.PsiRizerio.controller.AuthController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private AuthController authController;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController();
    }

    @Test
    void validarSessao_TokenValido() {
        ResponseEntity<?> responseEntity = authController.validarSessao();

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Sessão ativa.", responseEntity.getBody());
    }

    @Test
    void logout_DeveRemoverCookie() {
        authController.logout(response);

        verify(response, times(1)).addCookie(argThat(cookie -> {
            assertEquals("jwt", cookie.getName());
            assertNull(cookie.getValue());
            assertEquals(0, cookie.getMaxAge());
            assertTrue(cookie.isHttpOnly());
            assertEquals("/", cookie.getPath());
            return true;
        }));
    }
}