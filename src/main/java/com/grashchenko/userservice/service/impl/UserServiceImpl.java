package com.grashchenko.userservice.service.impl;

import com.grashchenko.userservice.exception.StorageDataNotFoundException;
import com.grashchenko.userservice.mapper.UserMapper;
import com.grashchenko.userservice.model.dto.UserDTO;
import com.grashchenko.userservice.model.entity.User;
import com.grashchenko.userservice.repository.UserRepository;
import com.grashchenko.userservice.service.UserService;
import com.grashchenko.userservice.strategy.ValidationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.grashchenko.userservice.util.ValidationUtil.checkSearchFields;
import static com.grashchenko.userservice.util.ValidationUtil.checkStrategy;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Map<String, ValidationStrategy> validationStrategies;

    @Transactional
    public User create(UserDTO userDTO, String source) {
        ValidationStrategy strategy = validationStrategies.get(source);
        checkStrategy(strategy, source, userDTO);
        User user = userRepository.save(userMapper.toUser(userDTO));
        log.info("client created with id {}", user.getId());
        return user;
    }

    public UserDTO get(UUID id) {
        log.info("get client with id {}", id);
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
        checkSearchFields(lastName, firstName, middleName, mobilePhone, email);
        return userMapper.toDtoes(userRepository.searchUsers(lastName,
                firstName, middleName, mobilePhone, email).orElseThrow(() ->
                new StorageDataNotFoundException("Client not found")));
    }
}
