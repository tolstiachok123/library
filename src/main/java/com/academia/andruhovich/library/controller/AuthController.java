package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.AuthRequestDto;
import com.academia.andruhovich.library.dto.AuthResponseDto;
import com.academia.andruhovich.library.dto.UserDto;
import com.academia.andruhovich.library.security.CustomPrincipal;
import com.academia.andruhovich.library.security.JwtTokenProvider;
import com.academia.andruhovich.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@Validated
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService service;


    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto requestDto) {
        return authenticate(requestDto.getEmail(), requestDto.getPassword());
    }

    @PostMapping(value = "/registration")
    public AuthResponseDto register(@RequestBody UserDto userDto) {
        service.add(userDto);
        return authenticate(userDto.getEmail(), userDto.getPassword());
    }

    protected AuthResponseDto authenticate(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        String token = jwtTokenProvider.createToken(email, customPrincipal.getRoles());

        return new AuthResponseDto(email, token);
    }
}
