package com.grashchenko.userservice.strategy;

import com.grashchenko.userservice.model.dto.UserDTO;

public interface ValidationStrategy {
    boolean validate(UserDTO userDTO);
}
