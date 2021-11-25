package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.UserDto;
import com.academia.andruhovich.library.exception.BusyEmailException;
import com.academia.andruhovich.library.mapper.UserMapper;
import com.academia.andruhovich.library.model.Role;
import com.academia.andruhovich.library.model.User;
import com.academia.andruhovich.library.repository.RoleRepository;
import com.academia.andruhovich.library.repository.UserRepository;
import com.academia.andruhovich.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.academia.andruhovich.library.exception.ErrorMessages.BUSY_EMAIL;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_ROLE = "USER";

    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDto add(UserDto dto) {
        User user = mapper.dtoToModel(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.getByName(DEFAULT_ROLE).get();
        user.setRoles(Set.of(role));

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BusyEmailException(String.format(BUSY_EMAIL, user.getEmail()));
        }

        return mapper.modelToDto(userRepository.save(user));
    }

}
