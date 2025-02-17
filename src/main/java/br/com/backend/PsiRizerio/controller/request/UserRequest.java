package br.com.backend.PsiRizerio.controller.request;

public record UserRequest(
    String name,
    String email,
    String password,
    String phone,
    String address,
    String cpf
) {
}
