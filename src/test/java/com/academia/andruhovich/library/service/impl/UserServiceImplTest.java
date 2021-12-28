package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.dto.UserDto;
import com.academia.andruhovich.library.mapper.UserMapper;
import com.academia.andruhovich.library.model.Role;
import com.academia.andruhovich.library.model.User;
import com.academia.andruhovich.library.repository.RoleRepository;
import com.academia.andruhovich.library.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.academia.andruhovich.library.util.Constants.ENCRYPTED_PASSWORD;
import static com.academia.andruhovich.library.util.RoleHelper.createExistingRole;
import static com.academia.andruhovich.library.util.UserHelper.createNewUser;
import static com.academia.andruhovich.library.util.UserHelper.createExistingUser;
import static com.academia.andruhovich.library.util.UserHelper.createExistingUserDto;
import static com.academia.andruhovich.library.util.UserHelper.createNewUserDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private UserServiceImpl service;

    @Mock
    private UserMapper mapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new UserServiceImpl(mapper, userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void add() {
        //given
        when(mapper.dtoToModel(any())).thenReturn(createNewUser());
        when(passwordEncoder.encode(any())).thenReturn(ENCRYPTED_PASSWORD);
        when(roleRepository.getByName(any())).thenReturn(Optional.of(createExistingRole()));
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(createExistingUser());
        when(mapper.modelToDto(any())).thenReturn(createExistingUserDto());
        //when
        UserDto dto = service.add(createNewUserDto());
        //then
        assertNotNull(dto.getRoles());
        assertNull(dto.getPassword());
    }

}
