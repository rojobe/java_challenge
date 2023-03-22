package com.java.challenge.java_challenge.service;

import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.ErrorResponseException;

import java.util.Optional;

public interface UserService {
    User createUser(User user) throws ErrorResponseException;

    User getUserByUsernameAndPassword(String name, String password) throws ErrorResponseException;

    User getUserByUsername(String username);

    User getUserByEmail(String email) throws ErrorResponseException;;

}
