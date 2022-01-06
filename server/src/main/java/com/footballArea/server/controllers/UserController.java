package com.footballArea.server.controllers;

import com.footballArea.server.model.User;
import com.footballArea.server.model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.footballArea.server.service.UserService;


@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/user";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping({"/login"})
    @ResponseStatus(HttpStatus.OK)
    public User login(@RequestBody UserDTO userDTO)
    {
        return userService.authenticateUser(userDTO);
    }

}
