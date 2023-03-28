package com.java.challenge.java_challenge.utils;

import com.java.challenge.java_challenge.dto.UserDTO;
import com.java.challenge.java_challenge.entity.User;

public class UserUtils {

    public static User getUser(){
        User user = new User();
        user.setUsername("rodrigobenito");
        user.setEmail("rodrigobenito@mail.com");
        user.setPassword("a2asfGf6");
        return user;
    }

    public static UserDTO getUserDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("rodrigobenito");
        userDTO.setEmail("rodrigobenito@mail.com");
        userDTO.setPassword("a2asfGf6");
        return userDTO;
    }

    public static UserDTO getUserDTOInvalidEmail(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("rodrigobenito");
        userDTO.setEmail("rodrigobenitomail.com"); // falta arroba ( @ ) en el mail
        userDTO.setPassword("a2asfGf6");
        return userDTO;
    }

    public static UserDTO getUserDTOInvalidFormatPassword(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("rodrigobenito");
        userDTO.setEmail("rodrigobenito@mail.com");
        userDTO.setPassword("a2aHsfGf6"); // el password contiene 2 mayusculas
        return userDTO;
    }
}
