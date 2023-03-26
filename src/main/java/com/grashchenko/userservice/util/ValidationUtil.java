package com.grashchenko.userservice.util;

import com.grashchenko.userservice.model.dto.UserDTO;
import com.grashchenko.userservice.strategy.ValidationStrategy;
import lombok.experimental.UtilityClass;

import javax.validation.ValidationException;
import java.util.Objects;
import java.util.stream.Stream;

@UtilityClass
public class ValidationUtil {

    public static void checkStrategy(ValidationStrategy strategy, String source, UserDTO userDTO) {
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid source: " + source);
        }

        if (!strategy.validate(userDTO)) {
            throw new ValidationException("Invalid client data for source: " + source);
        }
    }

    public static void checkSearchFields(String lastName,
                                         String firstName,
                                         String middleName,
                                         String mobilePhone,
                                         String email) {
        if(Stream.of(lastName, firstName, middleName, mobilePhone, email).allMatch(Objects::isNull)) {
            throw new IllegalArgumentException("At least one search field must be specified");
        }
    }
}
