package br.com.backend.PsiRizerio.persistence.entities;

import br.com.backend.PsiRizerio.enums.StatusUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "cliente")
public class Usuario {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "nome", columnDefinition = "VARCHAR(60)")
        private String nome;

        @Column(name = "cpf", columnDefinition = "CHAR(14)", unique = true)
        private String cpf;

        @Column(name = "email", columnDefinition = "VARCHAR(80)", unique = true)
        private String email;

        @Column(name = "senha", columnDefinition = "VARCHAR(100)")
        private String senha;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", columnDefinition = "VARCHAR(20)")
        private StatusUsuario status;

        @ManyToOne
        @JoinColumn(name = "fk_plano", referencedColumnName = "id")
        @JsonIgnore
        private Plano fkPlano;

        @ManyToOne
        @JoinColumn(name = "fk_endereco", referencedColumnName = "id")
        @JsonIgnore
        private Endereco fkEndereco;

        @CreatedDate
        @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
        private LocalDateTime createdAt;

        @CreatedDate
        @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
        private LocalDateTime updatedAt;

        public Usuario(String email, String encode) {
        }

        public Usuario() {
        }

        public Usuario(Integer id, String nome, String cpf, String email, String senha, StatusUsuario status, Plano fkPlano, Endereco fkEndereco, LocalDateTime createdAt, LocalDateTime updatedAt) {
                this.id = id;
                this.nome = nome;
                this.cpf = cpf;
                this.email = email;
                this.senha = senha;
                this.status = status;
                this.fkPlano = fkPlano;
                this.fkEndereco = fkEndereco;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getCpf() {
                return cpf;
        }

        public void setCpf(String cpf) {
                this.cpf = cpf;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getSenha() {
                return senha;
        }

        public void setSenha(String senha) {
                this.senha = senha;
        }

        public Plano getFkPlano() {
                return fkPlano;
        }

        public void setFkPlano(Plano fkPlano) {
                this.fkPlano = fkPlano;
        }

        public Endereco getFkEndereco() {
                return fkEndereco;
        }

        public void setFkEndereco(Endereco fkEndereco) {
                this.fkEndereco = fkEndereco;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
                return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
        }

        public StatusUsuario getStatus() {
                return status;
        }

        public void setStatus(StatusUsuario status) {
                this.status = status;
        }
}
