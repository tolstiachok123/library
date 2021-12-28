package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @Size(min = 1, max = 255)
    private String firstName;
    @Size(min = 1, max = 255)
    private String lastName;
    @NotBlank
    @Email
    private String email;
    private Set<RoleDto> roles;
    @NotBlank
    @Size(min = 1)
    private String password;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

}
