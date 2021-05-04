package com.shop.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
//public class User {

    private Long id;

    @Email
    private String email;

    @Pattern(regexp = "(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}")
    private String password;

    private String name;

    private List<Check> checks;

    private UserType userType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getUserType());
    }

    @Override
    public String getUsername() {
        return getEmail();
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
