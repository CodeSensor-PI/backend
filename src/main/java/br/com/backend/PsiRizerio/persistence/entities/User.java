package br.com.backend.PsiRizerio.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
        @SequenceGenerator(name = "user_seq", sequenceName = "USERS_SEQ", allocationSize = 1)
        private Integer id;

        @Column(name = "name", columnDefinition = "VARCHAR(60)", nullable = false)
        private String name;

        @Column(name = "cpf", columnDefinition = "CHAR(14)", nullable = false)
        private String cpf;

        @Column(name = "email", columnDefinition = "VARCHAR(80)", nullable = false)
        private String email;

        @Column(name = "senha", columnDefinition = "VARCHAR(20)", nullable = false)
        private String senha;

        @JoinColumn(name = "fk_plano ", columnDefinition = "INT", nullable = false)
        private Integer fk_plano;

        @JoinColumn(name = "fk_endereco", columnDefinition = "INT", nullable = false)
        private Integer fk_endereco;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
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

        public Integer getFk_plano() {
                return fk_plano;
        }

        public void setFk_plano(Integer fk_plano) {
                this.fk_plano = fk_plano;
        }

        public Integer getFk_endereco() {
                return fk_endereco;
        }

        public void setFk_endereco(Integer fk_endereco) {
                this.fk_endereco = fk_endereco;
        }
}
