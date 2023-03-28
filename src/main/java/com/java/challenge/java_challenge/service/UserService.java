package com.java.challenge.java_challenge.service;

import com.java.challenge.java_challenge.dto.UserDTO;
import com.java.challenge.java_challenge.error.RepositoryException;

public interface UserService {
    UserDTO createUser(UserDTO userDTO) throws RepositoryException;

    UserDTO login(String email, String password) throws RepositoryException;

    //UserDTO getUserByUsernameAndPassword(String name, String password) throws RepositoryException;

    UserDTO getUserByEmail(String email) throws RepositoryException;;





}
