package com.grashchenko.userservice.service;

import com.grashchenko.userservice.model.dto.UserDTO;
import com.grashchenko.userservice.model.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User create(UserDTO userDTO, String source);

    UserDTO get(UUID id);

    List<UserDTO> searchUsers(String lastName,
                              String firstName,
                              String middleName,
                              String mobilePhone,
                              String email);
}
