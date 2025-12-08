package br.com.backend.PsiRizerio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.backend.PsiRizerio.security.GerenciadorTokenJwt;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints de sessão e autenticação via JWT.")
public class AuthController {

    @GetMapping("/validate")
    @Operation(
            summary = "Valida a sessão do usuário.",
            description = "Este endpoint retorna 200 caso o token JWT enviado no cookie seja válido. " +
                    "A validação é realizada automaticamente pelo filtro de autenticação."
    )
    @ApiResponse(responseCode = "200", description = "Sessão válida (token válido).")
    @ApiResponse(responseCode = "401", description = "Token ausente ou expirado.")
    @ApiResponse(responseCode = "403", description = "Token inválido.")
    public ResponseEntity<String> validarSessao() {
        return ResponseEntity.ok("Sessão ativa.");
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Encerra a sessão do usuário.",
            description = "Remove o cookie contendo o token JWT, efetuando o logout."
    )
    @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso.")
    public ResponseEntity<String> logout(HttpServletResponse response) {

        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // usar true se estivermos com HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);

        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Logout realizado com sucesso.");
    }
  
      @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Operation(summary = "Gerar JWT fake para testes", description = "Gera um JWT válido com dados fictícios para testar endpoints protegidos.")
    @GetMapping("/fake-token")
    public ResponseEntity<Map<String, String>> gerarFakeToken() {
        String username = "fakeuser";
        String role = "ROLE_USER";
        UsernamePasswordAuthenticationToken fakeAuth = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
        String token = gerenciadorTokenJwt.generateToken(fakeAuth);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}

