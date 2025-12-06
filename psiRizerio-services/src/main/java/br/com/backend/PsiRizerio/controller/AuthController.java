package br.com.backend.PsiRizerio.controller;

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
public class AuthController {

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

