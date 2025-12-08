package br.com.backend.PsiRizerio.persistence.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 6)
    private String codigo;

    @Column(nullable = false)
    private String email; // Email do usuário (paciente ou psicólogo)
    
    @Column(nullable = false)
    private String tipoUsuario; // "PACIENTE" ou "PSICOLOGO"

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public PasswordResetToken() {}

    public PasswordResetToken(String codigo, String email, String tipoUsuario, LocalDateTime expiryDate) {
        this.codigo = codigo;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.expiryDate = expiryDate;
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
}

