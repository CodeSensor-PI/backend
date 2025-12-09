# Fluxo de Redefinição de Senha

Este documento descreve o funcionamento do fluxo de redefinição de senha implementado no backend do PsiRizerio.

## Visão Geral
O fluxo permite que o usuário solicite a redefinição de senha informando seu e-mail. O sistema gera um token único, envia um e-mail com o link de redefinição e, ao acessar o link, o usuário pode cadastrar uma nova senha. O token expira após 1 hora e é removido após o uso.

## Entidades Envolvidas
- **Paciente**: Usuário que solicita a redefinição.
- **PasswordResetToken**: Armazena o token, o paciente relacionado e a data de expiração.

## Endpoints REST

### 1. Solicitar Redefinição de Senha
`POST /password-reset/request`

**Parâmetros:**
- `email` (String): E-mail do paciente cadastrado.

**Funcionamento:**
- O sistema verifica se o e-mail existe.
- Gera um token de redefinição vinculado ao paciente.
- Envia um e-mail com o link de redefinição para o paciente.

**Exemplo de requisição:**
```http
POST /password-reset/request?email=usuario@dominio.com
```
**Resposta:**
- 200 OK: "E-mail de redefinição enviado"
- 400 Bad Request: "Paciente não encontrado"

### 2. Redefinir Senha
`POST /password-reset/confirm`

**Parâmetros:**
- `token` (String): Token recebido por e-mail.
- `novaSenha` (String): Nova senha desejada.

**Funcionamento:**
- O sistema valida o token e verifica se está dentro do prazo de expiração.
- Atualiza a senha do paciente (criptografada).
- Remove o token do banco.

**Exemplo de requisição:**
```http
POST /password-reset/confirm?token=abc123&novaSenha=minhaNovaSenhaSegura
```
**Resposta:**
- 200 OK: "Senha redefinida com sucesso"
- 400 Bad Request: "Token inválido ou expirado"

## Segurança
- O token expira em 1 hora.
- O token é único por solicitação e removido após uso.
- A senha é armazenada apenas criptografada (hash).
- Nunca é possível recuperar a senha original do usuário.

## Observações
- O link enviado por e-mail deve ser ajustado conforme o domínio do frontend.
- O envio de e-mail utiliza RabbitMQ, conforme configuração do projeto.
- Recomenda-se que o frontend implemente uma tela para o usuário informar a nova senha ao acessar o link.

---

Dúvidas ou sugestões? Entre em contato com o time de desenvolvimento.

