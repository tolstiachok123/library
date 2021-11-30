package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.model.Role;

import static com.academia.andruhovich.library.util.Constants.DEFAULT_ROLE_NAME;
import static com.academia.andruhovich.library.util.Constants.ID;

public class RoleHelper {

    public static Role createExistingRole() {
        Role role = new Role();
        role.setId(ID);
        role.setName(DEFAULT_ROLE_NAME);
        return role;
    }

}
