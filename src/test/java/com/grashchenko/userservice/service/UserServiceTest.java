package com.grashchenko.userservice.service;

import com.grashchenko.userservice.exception.StorageDataNotFoundException;
import com.grashchenko.userservice.mapper.UserMapper;
import com.grashchenko.userservice.model.dto.UserDTO;
import com.grashchenko.userservice.model.entity.User;
import com.grashchenko.userservice.repository.UserRepository;
import com.grashchenko.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String SOURCE = "mail";
    private static final UserDTO USER_DTO = createUserDto();
    private static final User USER = createUser();
    private static final List<User> USER_LIST = Arrays.asList(USER);

    @BeforeEach
    void setUp() {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(USER);
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        Mockito.when(userRepository.searchUsers(Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(USER_LIST));
        Mockito.when(userMapper.toUser(Mockito.any(UserDTO.class))).thenReturn(USER);
        Mockito.when(userMapper.toUserDTO(Mockito.any(User.class))).thenReturn(USER_DTO);
    }

    @Test
    void createUser_validData_createsUser() {
        userService.create(USER_DTO, SOURCE);
        Mockito.verify(userRepository, Mockito.times(1)).save(USER);
    }

    @Test
    void createUser_invalidSource_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> userService.create(USER_DTO, "invalidSource"));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    void createUser_invalidData_throwsException() {
        Mockito.when(userRepository.searchUsers(Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.empty());
        assertThrows(ValidationException.class, () -> userService.create(USER_DTO, SOURCE));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    void getUser_existingUser_returnsUser() {
        UserDTO userDTO = userService.get(USER_ID);
        assertEquals(USER_DTO, userDTO);
        Mockito.verify(userRepository, Mockito.times(1)).findById(USER_ID);
    }

    @Test
    void getUser_nonExistingUser_throwsException() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        assertThrows(StorageDataNotFoundException.class, () -> userService.get(USER_ID));
    }

    @Test
    void searchUser_existingUsers_returnsUsers() {
        List<UserDTO> userDTOList = userService.searchUsers("Doe", "John", "Adam", "1234567890", "johndoe@example.com");
        assertEquals(1, userDTOList.size());
        assertEquals(USER_DTO, userDTOList.get(0));
        Mockito.verify(userRepository, Mockito.times(1))
                .searchUsers(Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    private static UserDTO createUserDto() {
        UserDTO userDto = new UserDTO();
        userDto.setBankId("1234567890");
        userDto.setLastName("Doe");
        userDto.setFirstName("John");
        userDto.setMiddleName("Smith");
        userDto.setBirthDate(LocalDate.of(1980, 1, 1));
        userDto.setPassportNumber("1234 567890");
        userDto.setBornPlace("New York");
        userDto.setMobilePhone("71234567890");
        userDto.setEmail("johndoe@example.com");
        userDto.setRegistrationAddress("New York");
        userDto.setResidentialAddress("California");

        return userDto;
    }

    private static User createUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setBankId("bankId");
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setMiddleName("Smith");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setPassportNumber("1234 567890");
        user.setBornPlace("New York");
        user.setMobilePhone("7234567890");
        user.setEmail("johndoe@example.com");
        user.setRegistrationAddress("123 Main St, New York, NY");
        user.setResidentialAddress("456 Elm St, New York, NY");
        return user;
    }
}