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
@Setter
@Getter
@Table(name = "users")
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
        @SequenceGenerator(name = "user_seq", sequenceName = "USERS_SEQ", allocationSize = 1)
        private Long id;

        @Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
        private String name;

        @Column(name = "email", columnDefinition = "VARCHAR(255)", nullable = false)
        private String email;

        @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
        private String password;

        @Column(name = "phone", columnDefinition = "VARCHAR(255)", nullable = false)
        private String phone;

        @Column(name = "address", columnDefinition = "VARCHAR(255)", nullable = false)
        private String address;

        @Column(name = "cpf", columnDefinition = "VARCHAR(255)", nullable = false)
        private String cpf;

        public Long getId() {
                return id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getCpf() {
                return cpf;
        }

        public void setCpf(String cpf) {
                this.cpf = cpf;
        }
}
