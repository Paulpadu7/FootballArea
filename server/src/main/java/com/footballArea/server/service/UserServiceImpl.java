package com.footballArea.server.service;

import com.footballArea.server.model.User;
import com.footballArea.server.model.UserDTO;
import org.springframework.stereotype.Service;
import com.footballArea.server.repo.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User authenticateUser(UserDTO userDTO) {
        for(User user: userRepository.findAll())
        {
            if(user.getUsername().equals(userDTO.getUsername()) && (user.getPassword().equals(userDTO.getPassword())))
                return user;
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

}
