package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]*[-\\s]?([A-ZА-Я]?[a-zа-я]*[-\\s]?)*")
    private String firstName;
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]*[-\\s]?([A-ZА-Я]?[a-zа-я]*[-\\s]?)*")
    private String lastName;
    @NotNull
    private String email;
    private Set<RoleDto> roles;
    @NotNull
    private String password;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

}
