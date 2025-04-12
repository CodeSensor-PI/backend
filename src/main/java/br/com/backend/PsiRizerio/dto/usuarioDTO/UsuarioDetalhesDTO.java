package br.com.backend.PsiRizerio.dto.usuarioDTO;

import br.com.backend.PsiRizerio.persistence.entities.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UsuarioDetalhesDTO implements UserDetails {

  private final String nome;

  private final String email;

  private final String senha;

  public UsuarioDetalhesDTO(Usuario usuario) {
    this.nome = usuario.getNome();
    this.email = usuario.getEmail();
    this.senha = usuario.getSenha();
  }

  public String getNome() {
    return nome;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return senha;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
