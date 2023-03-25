package com.grashchenko.userservice.strategy;

import com.grashchenko.userservice.model.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component(value = "mail")
public class MailValidationStrategy implements ValidationStrategy {

    @Override
    public boolean validate(UserDTO userDTO) {
        return userDTO.getFirstName() != null && userDTO.getEmail() != null;
    }
}
