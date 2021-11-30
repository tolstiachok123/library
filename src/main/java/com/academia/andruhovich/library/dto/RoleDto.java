package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Long id;
    private String name;
    private Set<AuthorityDto> authorities;

}
