package dev.auth.service;

import dev.auth.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

// UserDetails 타입을 구현한 커스텀 구현체
// DB에서 조회된 User Entity의 값을 시큐리티에서 사용할 수 있도록 UserDetails 타입으로 감싸주는 래퍼 역할
// 결국 DB에서 조회된 엔티티인 User가 가지고 있는 값들에 맞게 UserDetails를 바인딩
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

  // DB에서 받은 User Entity
  private final User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getAuthorities().stream()
      .map(authority -> new SimpleGrantedAuthority(authority.getName()))
      .collect(Collectors.toList());
    // DB에서 조회된 각 사용자의 권한(read, write)을 SimpleGrantedAuthority로 매핑
    // SimpleGrantedAuthority -> GrantedAuthority의 구현체
  }

  // 이따 비밀번호 비교할 때 DB에 있는 비밀번호 그대로 가져오기
  @Override
  public String getPassword() {
    return user.getPassword();
  }

  // username도 마찬가지
  @Override
  public String getUsername() {
    return user.getUsername();
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
