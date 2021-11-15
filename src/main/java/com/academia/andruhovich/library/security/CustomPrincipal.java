package com.academia.andruhovich.library.security;

import com.academia.andruhovich.library.model.Authority;
import com.academia.andruhovich.library.model.Role;
import com.academia.andruhovich.library.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomPrincipal extends User implements UserDetails {

    private final boolean enabled;

    public CustomPrincipal(Long id, String email, Set<Role> roles, String password, boolean enabled) {
        super(id, email, roles, password);
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<Authority> authorities = new HashSet<>();

        Set<Role> roles = super.getRoles();
        if (roles == null) {
            return grantedAuthorities;
        }
        roles.forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.addAll(role.getAuthorities());
        });

        authorities.forEach(authority -> grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName())));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
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
        return enabled;
    }
}
