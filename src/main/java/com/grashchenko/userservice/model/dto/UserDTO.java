package com.grashchenko.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String bankId;

    private String lastName;

    private String firstName;

    private String middleName;

    private LocalDate birthDate;

    @Pattern(regexp = "\\d{4} \\d{6}")
    private String passportNumber;

    private String bornPlace;

    @Pattern(regexp = "^7\\d{10}$", message = "Phone number must be in the format 7XXXXXXXXXX")
    private String mobilePhone;

    @Email
    private String email;

    private String registrationAddress;

    private String residentialAddress;
}
