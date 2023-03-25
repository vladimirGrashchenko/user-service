package com.grashchenko.userservice.strategy;

import com.grashchenko.userservice.model.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component(value = "gosuslugi")
public class GosuslugiValidationStrategy implements ValidationStrategy {
    @Override
    public boolean validate(UserDTO userDTO) {
        return userDTO.getBankId() != null && userDTO.getFirstName() != null
                && userDTO.getLastName() != null && userDTO.getMiddleName() != null
                && userDTO.getPassportNumber() != null && userDTO.getBirthDate() != null
                && userDTO.getBornPlace() != null && userDTO.getMobilePhone() != null;
    }
}
