package com.academia.andruhovich.library.security;

import com.academia.andruhovich.library.exception.ResourceNotFoundException;
import com.academia.andruhovich.library.model.User;
import com.academia.andruhovich.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.academia.andruhovich.library.exception.ErrorMessages.USER_NOT_FOUND;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public JwtUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND, email)));

        return new CustomPrincipal(
                user.getId(),
                user.getEmail(),
                user.getRoles(),
                user.getPassword(),
                true
        );
    }
}
