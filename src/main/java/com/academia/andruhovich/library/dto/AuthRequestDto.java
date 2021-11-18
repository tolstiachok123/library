package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {

    @NotBlank
    @Email(regexp = ".*[@].*")
    private String email;

    @NotBlank
    private String password;
}
