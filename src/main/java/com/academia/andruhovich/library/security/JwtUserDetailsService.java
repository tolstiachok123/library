package com.academia.andruhovich.library.security;

import com.academia.andruhovich.library.exception.NotFoundException;
import com.academia.andruhovich.library.model.User;
import com.academia.andruhovich.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public JwtUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.getByEmail(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return new CustomPrincipal(
                user.getId(),
                user.getEmail(),
                user.getRoles(),
                user.getPassword(),
                true
        );
    }
}
