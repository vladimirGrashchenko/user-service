package com.grashchenko.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grashchenko.userservice.EntityGenerator;
import com.grashchenko.userservice.exception.ErrorMessage;
import com.grashchenko.userservice.exception.StorageDataNotFoundException;
import com.grashchenko.userservice.handler.ControllerExceptionHandler;
import com.grashchenko.userservice.model.dto.UserDTO;
import com.grashchenko.userservice.model.entity.User;
import com.grashchenko.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ValidationException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    private UserController userController;

    private static final String REST_URL = UserController.REST_URL;

    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    private ObjectMapper objectMapper;

    private User testUser;

    private UserDTO testUserDTO;

    private ErrorMessage errorMessage;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

        testUser = EntityGenerator.generateUser();

        testUserDTO = EntityGenerator.generateUserDTO();
    }

    @Test
    void testCreateUser_Successful() throws Exception {
        when(userService.create(any(UserDTO.class), eq("source"))).thenReturn(testUser);

        mockMvc.perform(post(REST_URL)
                        .header("x-Source", "source")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString(REST_URL + "/" + testUser.getId())))
                .andExpect(content().json(objectMapper.writeValueAsString(testUser)));
    }

    @Test
    public void testCreateUser_InvalidSource() throws Exception {
        errorMessage = new ErrorMessage("Invalid source: invalid_source");
        when(userService.create(any(UserDTO.class), eq("invalid_source"))).thenThrow(new IllegalArgumentException("Invalid source: invalid_source"));

        mockMvc.perform(post(REST_URL)
                        .header("x-Source", "invalid_source")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(errorMessage)));
    }

    @Test
    public void testCreateUser_InvalidData() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .firstName(null).build();
        errorMessage = new ErrorMessage("Invalid client data for source: source");
        when(userService.create(any(UserDTO.class), eq("mail")))
                .thenThrow(new ValidationException("Invalid client data for source: source"));

        mockMvc.perform(post(REST_URL)
                        .header("x-Source", "mail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(errorMessage)));
    }

    @Test
    void testGetUser_Successful() throws Exception {
        UUID userId = testUser.getId();

        when(userService.get(userId)).thenReturn(testUserDTO);

        mockMvc.perform(get(REST_URL + "/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testUserDTO)));
    }

    @Test
    public void testGetUser_InvalidId() throws Exception {
        UUID invalidUserId = UUID.randomUUID();
        errorMessage = new ErrorMessage("Client with id " + invalidUserId + " not found");

        when(userService.get(invalidUserId))
                .thenThrow(new StorageDataNotFoundException("Client with id " + invalidUserId + " not found"));

        mockMvc.perform(get(REST_URL + "/{id}", invalidUserId))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(errorMessage)));
    }

    @Test
    void testSearch_Successful() throws Exception {
        String lastName = "last_name";
        String firstName = "first_name";
        String middleName = "middle_name";
        String mobilePhone = "71234567890";
        String email = "test@example.com";

        List<UserDTO> expectedUserDTOs = Collections.singletonList(testUserDTO);
        when(userService.searchUsers(lastName, firstName, middleName, mobilePhone, email)).thenReturn(expectedUserDTOs);

        mockMvc.perform(get(REST_URL)
                        .param("lastName", lastName)
                        .param("firstName", firstName)
                        .param("middleName", middleName)
                        .param("mobilePhone", mobilePhone)
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedUserDTOs)));
    }

    @Test
    public void testSearchUsers_InvalidSearchFields() throws Exception {
        errorMessage = new ErrorMessage("At least one search field must be specified");
        when(userService.searchUsers(null, null, null, null, null))
                .thenThrow(new IllegalArgumentException("At least one search field must be specified"));

        mockMvc.perform(get(REST_URL))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(errorMessage)));
    }
}

