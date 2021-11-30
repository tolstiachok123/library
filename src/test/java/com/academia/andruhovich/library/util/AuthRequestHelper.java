package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.dto.AuthRequestDto;

import static com.academia.andruhovich.library.util.Constants.EMAIL;
import static com.academia.andruhovich.library.util.Constants.PASSWORD;

public class AuthRequestHelper {

    public static AuthRequestDto createExistingUserAuthRequestDto() {
        AuthRequestDto authRequestDto = new AuthRequestDto();
        authRequestDto.setEmail(EMAIL);
        authRequestDto.setPassword(PASSWORD);
        return authRequestDto;
    }
}
