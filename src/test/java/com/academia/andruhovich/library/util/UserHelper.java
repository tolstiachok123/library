package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.dto.RoleDto;
import com.academia.andruhovich.library.dto.UserDto;
import com.academia.andruhovich.library.model.User;

import java.util.Set;

import static com.academia.andruhovich.library.util.Constants.EMAIL;
import static com.academia.andruhovich.library.util.Constants.NOW;
import static com.academia.andruhovich.library.util.Constants.PASSWORD;
import static com.academia.andruhovich.library.util.Constants.ENCRYPTED_PASSWORD;
import static com.academia.andruhovich.library.util.Constants.ID;
import static com.academia.andruhovich.library.util.Constants.FIRST_NAME;
import static com.academia.andruhovich.library.util.Constants.LAST_NAME;
import static com.academia.andruhovich.library.util.Constants.DEFAULT_ROLE_NAME;
import static com.academia.andruhovich.library.util.Constants.UNREGISTERED_EMAIL;

public class UserHelper {

    public static User createNewUser() {
        User user = new User();
        user.setEmail(UNREGISTERED_EMAIL);
        user.setPassword(PASSWORD);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setCreatedAt(NOW);
        user.setUpdatedAt(NOW);
        return user;
    }

    public static User createExistingUser() {
        User user = new User();
        user.setId(ID);
        user.setEmail(EMAIL);
        user.setPassword(ENCRYPTED_PASSWORD);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setCreatedAt(NOW);
        user.setUpdatedAt(NOW);
        return user;
    }

    public static UserDto createNewUserDto() {
        UserDto dto = new UserDto();
        dto.setEmail(EMAIL);
        dto.setPassword(PASSWORD);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        return dto;
    }

    public static UserDto createExistingUserDto() {
        UserDto dto = new UserDto();
        dto.setId(ID);
        dto.setEmail(EMAIL);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setRoles(Set.of(new RoleDto(ID, DEFAULT_ROLE_NAME, null)));
        return dto;
    }

    public static UserDto createUnregisteredUserDto() {
        UserDto dto = new UserDto();
        dto.setEmail(UNREGISTERED_EMAIL);
        dto.setPassword(PASSWORD);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        return dto;
    }
}
