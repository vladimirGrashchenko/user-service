package com.grashchenko.userservice;

import com.grashchenko.userservice.model.dto.UserDTO;
import com.grashchenko.userservice.model.entity.User;

import java.time.LocalDate;
import java.util.UUID;

public class EntityGenerator {

    private static final String BANK_ID = "bank_id";
    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final LocalDate BIRTH_DATE = LocalDate.of(2000, 1, 1);
    private static final String PASSPORT_NUMBER = "1234 567890";
    private static final String BORN_PLACE = "born_place";
    private static final String MOBILE_PHONE = "71234567890";
    private static final String EMAIL = "test@example.com";
    private static final String REGISTRATION_ADDRESS = "registration_address";
    private static final String RESIDENTIAL_ADDRESS = "residential_address";

    public static User generateUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .bankId(BANK_ID)
                .lastName(LAST_NAME)
                .firstName(FIRST_NAME)
                .middleName(MIDDLE_NAME)
                .birthDate(BIRTH_DATE)
                .passportNumber(PASSPORT_NUMBER)
                .bornPlace(BORN_PLACE)
                .mobilePhone(MOBILE_PHONE)
                .email(EMAIL)
                .registrationAddress(REGISTRATION_ADDRESS)
                .residentialAddress(RESIDENTIAL_ADDRESS)
                .build();
    }

    public static UserDTO generateUserDTO() {
        return UserDTO.builder()
                .bankId(BANK_ID)
                .lastName(LAST_NAME)
                .firstName(FIRST_NAME)
                .middleName(MIDDLE_NAME)
                .birthDate(BIRTH_DATE)
                .passportNumber(PASSPORT_NUMBER)
                .bornPlace(BORN_PLACE)
                .mobilePhone(MOBILE_PHONE)
                .email(EMAIL)
                .registrationAddress(REGISTRATION_ADDRESS)
                .residentialAddress(RESIDENTIAL_ADDRESS)
                .build();
    }
}
