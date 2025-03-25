package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import com.example.demo.entities.User;
import com.example.demo.service.hashing.SHA256HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private SHA256HashService hashService;

    @Autowired
    private UserRepository userRepository;

    //Adds user to the database
    public void AddUser(String username, String password, String role) throws NoSuchAlgorithmException {
        User user = new User(username, hashService.hashText(password), role);
        userRepository.save(user);
    }

    //Checks whether a user exists or not in the database by comparing username (room id) and password
    public User CheckUserWithCredentials(String username, String password) throws NoSuchAlgorithmException {

        String p = hashService.hashText(password);
        User user = userRepository.GetUserWithCredentials(username, p);

        if (user != null){
            return user;
        }
        return null;
    }
}

