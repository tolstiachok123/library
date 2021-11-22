package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.AuthRequestDto;
import com.academia.andruhovich.library.dto.AuthResponseDto;
import com.academia.andruhovich.library.security.CustomPrincipal;
import com.academia.andruhovich.library.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto requestDto) {
        String email = requestDto.getEmail();

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        String token = jwtTokenProvider.createToken(email, customPrincipal.getRoles());

        return new AuthResponseDto(email, token);
    }
}
