package com.java.challenge.java_challenge.service;

import com.java.challenge.java_challenge.dto.UserDTO;
import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.ErrorResponseException;

public interface UserService {
    UserDTO createUser(UserDTO userDTO) throws ErrorResponseException;

    UserDTO getUserByUsernameAndPassword(String name, String password) throws ErrorResponseException;

    UserDTO getUserByEmail(String email) throws ErrorResponseException;;

    UserDTO login(String email, String password) throws ErrorResponseException;

}
