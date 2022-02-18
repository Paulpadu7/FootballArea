package com.footballArea.server.service;

import com.footballArea.server.model.User;
import com.footballArea.server.model.UserDTO;

import java.util.List;

public interface UserService {

    public User authenticateUser(UserDTO userDTO);
    public List<User> getAll();
}
