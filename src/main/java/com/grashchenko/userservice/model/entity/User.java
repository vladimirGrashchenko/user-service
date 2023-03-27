package com.grashchenko.userservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "bank_id")
    private String bankId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "passport_number", unique = true)
    @Pattern(regexp = "\\d{4} \\d{6}", message = "Passport number must be in the format ХХХХ ХХХХХХ")
    private String passportNumber;

    @Column(name = "born_place")
    private String bornPlace;

    @Column(name = "mobile_phone", unique = true)
    @Pattern(regexp = "^7\\d{10}$", message = "Phone number must be in the format 7XXXXXXXXXX")
    private String mobilePhone;

    @Column(name = "email")
    @Email(message = "Must be in the email format")
    private String email;

    @Column(name = "registration_address")
    private String registrationAddress;

    @Column(name = "residential_address")
    private String residentialAddress;

}
