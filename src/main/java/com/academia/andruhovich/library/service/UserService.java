package com.academia.andruhovich.library.service;

import com.academia.andruhovich.library.dto.UserDto;
import com.academia.andruhovich.library.model.Order;
import com.academia.andruhovich.library.model.User;

public interface UserService {

    UserDto add(UserDto dto);

    User getCurrent();

    void setOrderToCurrentUser(Order order);
}
