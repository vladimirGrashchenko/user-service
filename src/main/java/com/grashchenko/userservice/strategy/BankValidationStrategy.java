package com.grashchenko.userservice.strategy;

import com.grashchenko.userservice.model.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component(value = "bank")
public class BankValidationStrategy implements ValidationStrategy {
    @Override
    public boolean validate(UserDTO userDTO) {
        return userDTO.getBankId() != null && userDTO.getFirstName() != null
                && userDTO.getLastName() != null && userDTO.getMiddleName() != null
                && userDTO.getPassportNumber() != null && userDTO.getBirthDate() != null;
    }
}
