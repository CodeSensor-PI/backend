package br.com.backend.PsiRizerio.persistence.entities;

import br.com.backend.PsiRizerio.enums.StatusUsuario;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
        @SequenceGenerator(name = "user_seq", sequenceName = "USERS_SEQ", allocationSize = 1)
        private Integer id;

        @Column(name = "nome", columnDefinition = "VARCHAR(60)")
        private String nome;

        @Column(name = "cpf", columnDefinition = "CHAR(14)", unique = true)
        private String cpf;

        @Column(name = "email", columnDefinition = "VARCHAR(80)", unique = true)
        private String email;

        @Column(name = "senha", columnDefinition = "VARCHAR(20)")
        private String senha;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", columnDefinition = "VARCHAR(20)")
        private StatusUsuario status;

        @ManyToOne
        @JoinColumn(name = "fk_plano", referencedColumnName = "id")
        private Plano fkPlano;

        @ManyToOne
        @JoinColumn(name = "fk_endereco", referencedColumnName = "id")
        private Endereco fkEndereco;

        @CreatedDate
        @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
        private LocalDateTime createdAt;

        @CreatedDate
        @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
        private LocalDateTime updatedAt;

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
}
