package com.grashchenko.userservice.service;

import com.grashchenko.userservice.Exception.StorageDataNotFoundException;
import com.grashchenko.userservice.mapper.UserMapper;
import com.grashchenko.userservice.model.dto.UserDTO;
import com.grashchenko.userservice.model.entity.User;
import com.grashchenko.userservice.repository.UserRepository;
import com.grashchenko.userservice.strategy.ValidationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Map<String, ValidationStrategy> validationStrategies;

    @Transactional
    public void create(UserDTO userDTO, String source) {
        ValidationStrategy strategy = validationStrategies.get(source);

        if (strategy == null) {
            throw new IllegalArgumentException("Invalid source: " + source);
        }

        if (!strategy.validate(userDTO)) {
            throw new ValidationException("Invalid client data for source: " + source);
        }
        userRepository.save(userMapper.toUser(userDTO));
        log.info("client created");
    }

    public UserDTO get(UUID id) {
        log.info("get client");
        User user = userRepository.findById(id).orElseThrow(() ->
                new StorageDataNotFoundException("Client not found"));
        return userMapper.toUserDTO(user);
    }

    public List<UserDTO> searchUsers(String lastName,
                                     String firstName,
                                     String middleName,
                                     String mobilePhone,
                                     String email) {
        log.info("search client");
        if(Stream.of(lastName, firstName, middleName, mobilePhone, email).allMatch(Objects::isNull)) {
            throw new IllegalArgumentException("At least one search field must be specified");
        }
        return userMapper.toDtoes(userRepository.searchUsers(lastName,
                firstName, middleName, mobilePhone, email).orElseThrow(() ->
                new StorageDataNotFoundException("Client not found")));
    }
}
