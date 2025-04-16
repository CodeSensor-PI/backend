package br.com.backend.PsiRizerio.dto.usuarioDTO;

import jakarta.validation.constraints.NotBlank;

public class UsuarioSenhaUpdateDTO {
    @NotBlank
    private String senha;

    @NotBlank
    private String novaSenha;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}