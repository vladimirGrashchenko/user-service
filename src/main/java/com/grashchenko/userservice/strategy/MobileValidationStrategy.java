package com.grashchenko.userservice.strategy;

import com.grashchenko.userservice.model.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component(value = "mobile")
public class MobileValidationStrategy implements ValidationStrategy {
    @Override
    public boolean validate(UserDTO userDTO) {
        return userDTO.getMobilePhone() != null;
    }
}
