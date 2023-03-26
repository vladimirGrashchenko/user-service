package com.grashchenko.userservice.controller;

import com.grashchenko.userservice.model.dto.UserDTO;
import com.grashchenko.userservice.model.entity.User;
import com.grashchenko.userservice.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = UserController.REST_URL)
public class UserController {

    static final String REST_URL = "/api/v1/users";

    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestHeader("x-Source") String source,
                                             @Valid @RequestBody UserDTO userDTO) {
        User created = userService.create(userDTO, source);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri()).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> search(@RequestParam(required = false) String lastName,
                                                @RequestParam(required = false) String firstName,
                                                @RequestParam(required = false) String middleName,
                                                @RequestParam(required = false) String mobilePhone,
                                                @RequestParam(required = false) String email) {
        return ResponseEntity.ok(userService.searchUsers(lastName, firstName, middleName, mobilePhone, email));
    }

}
