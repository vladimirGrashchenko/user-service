package com.grashchenko.userservice.service;

import com.grashchenko.userservice.EntityGenerator;
import com.grashchenko.userservice.exception.StorageDataNotFoundException;
import com.grashchenko.userservice.mapper.UserMapper;
import com.grashchenko.userservice.model.dto.UserDTO;
import com.grashchenko.userservice.model.entity.User;
import com.grashchenko.userservice.repository.UserRepository;
import com.grashchenko.userservice.service.impl.UserServiceImpl;
import com.grashchenko.userservice.strategy.ValidationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ValidationException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ValidationStrategy validationStrategy;

    @Mock
    private Map<String, ValidationStrategy> validationStrategies;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;
    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        user = EntityGenerator.generateUser();

        userDTO = EntityGenerator.generateUserDTO();

        userId = UUID.randomUUID();
    }
    @Test
    void testCreate_Successful() {
        when(validationStrategies.get("mail")).thenReturn(validationStrategy);
        when(validationStrategy.validate(userDTO)).thenReturn(true);
        when(userMapper.toUser(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.create(userDTO, "mail");

        assertThat(result, is(user));
        verify(validationStrategy).validate(userDTO);
        verify(userMapper).toUser(userDTO);
        verify(userRepository).save(user);
    }

    @Test
    void testCreateUsers_InvalidSource() {
        when(validationStrategies.get("invalid_source")).thenReturn(validationStrategy);
        when(validationStrategy.validate(userDTO)).thenReturn(false);

        assertThrows(ValidationException.class, () -> userService.create(userDTO, "invalid_source"));

        verify(validationStrategy).validate(userDTO);
        verifyNoMoreInteractions(userRepository, userMapper);
    }

    @Test
    void testCreateUsers_ShouldThrowExceptionWithInvalidSource() {
        assertThrows(IllegalArgumentException.class, () -> userService.create(userDTO, "mail"));

        verifyNoInteractions(userRepository, userMapper, validationStrategy);
    }

    @Test
    void testGetUser_Successful() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO actualUserDTO = userService.get(userId);

        assertEquals(userDTO, actualUserDTO);
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toUserDTO(user);
    }

    @Test
    void testGetUser_NotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(StorageDataNotFoundException.class, () -> userService.get(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, never()).toUserDTO(any());
    }

    @Test
    void testSearchUsers_Successful() {
        List<User> userList = Collections.singletonList(user);
        when(userRepository.searchUsers("lastName", null, null, null, null))
                .thenReturn(userList);
        when(userMapper.toDtoes(userList)).thenReturn(Collections.singletonList(userDTO));

        List<UserDTO> result = userService.searchUsers("lastName", null, null, null, null);

        assertThat(result, is(Collections.singletonList(userDTO)));
        verify(userRepository).searchUsers("lastName", null, null, null, null);
        verify(userMapper).toDtoes(userList);
    }

    @Test
    void testSearchUsersShould_WithAllSearchFieldsNull() {
        assertThrows(IllegalArgumentException.class, () -> userService
                .searchUsers(null, null, null, null, null));
        verifyNoInteractions(userRepository, userMapper);
    }
}

