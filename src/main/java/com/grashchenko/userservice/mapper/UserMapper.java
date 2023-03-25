package com.grashchenko.userservice.mapper;

import com.grashchenko.userservice.model.dto.UserDTO;
import com.grashchenko.userservice.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);

    List<UserDTO> toDtoes(List<User> users);
}
