package br.com.backend.PsiRizerio.controller.response;

import lombok.Builder;

@Builder
public record UserResponse(
    Long id,
    String name,
    String email,
    String password,
    String phone,
    String address,
    String cpf
) {
}
