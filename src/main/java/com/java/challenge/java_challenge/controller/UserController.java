package com.java.challenge.java_challenge.controller;

import com.java.challenge.java_challenge.dto.SearchDTO;
import com.java.challenge.java_challenge.dto.UserDTO;
import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.ErrorMessage;
import com.java.challenge.java_challenge.error.RepositoryException;
import com.java.challenge.java_challenge.service.UserService;
import com.java.challenge.java_challenge.util.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
public class UserController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public UserController(UserService userService, JwtTokenUtil jwtTokenUtil){
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> signUpUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> loginUser(@RequestHeader String token) {
        return ResponseEntity.ok(userService.getUserByEmail(jwtTokenUtil.getEmailFromToken(token)));
    }

    /*

    @GetMapping(value = "/login-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginUserWithPassword(@RequestBody User user) {//TODO Convertir a userDTO - retornar ResponseEntity<UserDTO>
        try {

            return new ResponseEntity<>(userService.login(user.getEmail(), user.getPassword()), HttpStatus.OK);

        } catch (RepositoryException repositoryException) {

            return new ResponseEntity<>(repositoryException.getErrorList(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchUser(@RequestBody SearchDTO searchDTO, @RequestHeader String token) {
        try {
            if (searchDTO.getEmail().equalsIgnoreCase(jwtTokenUtil.getEmailFromToken(token))) {
                return ResponseEntity.ok(userService.getUserByEmail(searchDTO.getEmail()));
            } else {
                ErrorMessage userNotFound = new ErrorMessage();
                userNotFound.setTimeStamp(LocalDate.now());
                userNotFound.setCodigo(204);
                userNotFound.setDetail("User not found");
                return new ResponseEntity<>(userNotFound, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            ErrorMessage notAuthorized = new ErrorMessage();
            notAuthorized.setTimeStamp(LocalDate.now());
            notAuthorized.setCodigo(401);
            notAuthorized.setDetail("You are not authorized");
            return new ResponseEntity<>(notAuthorized, HttpStatus.UNAUTHORIZED);
        }

    }

     */
}
