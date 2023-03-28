package com.java.challenge.java_challenge.controller;

import com.java.challenge.java_challenge.dto.SearchDTO;
import com.java.challenge.java_challenge.dto.UserDTO;
import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.Error;
import com.java.challenge.java_challenge.error.ErrorResponseException;
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

    //@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> signUpUser(@RequestBody UserDTO userDTO){//TODO retornar ResponseEntity<UserDTO>
        try{
            UserDTO userResult = userService.createUser(userDTO);
            return ResponseEntity.ok(userResult);
        }catch(ErrorResponseException errorResponseException){//TODO Implementar ControllerAdvice
            return new ResponseEntity<>(errorResponseException.getErrorList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginUser(@RequestHeader String token) {//TODO retornar DTO
        try {

            UserDTO userData = userService.getUserByEmail(jwtTokenUtil.getEmailFromToken(token));

            return new ResponseEntity<>(userData, HttpStatus.OK);

        } catch (ErrorResponseException errorResponseException) {

            return new ResponseEntity<>(errorResponseException.getErrorList(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/login-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginUserWithPassword(@RequestBody User user) {//TODO Convertir a userDTO - retornar ResponseEntity<UserDTO>
        try {

            return new ResponseEntity<>(userService.login(user.getEmail(), user.getPassword()), HttpStatus.OK);

        } catch (ErrorResponseException errorResponseException) {

            return new ResponseEntity<>(errorResponseException.getErrorList(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchUser(@RequestBody SearchDTO searchDTO, @RequestHeader String token) {
        try {
            if (searchDTO.getEmail().equalsIgnoreCase(jwtTokenUtil.getEmailFromToken(token))) {
                return ResponseEntity.ok(userService.getUserByEmail(searchDTO.getEmail()));
            } else {
                Error userNotFound = new Error();
                userNotFound.setTimeStamp(LocalDate.now());
                userNotFound.setCodigo(204);
                userNotFound.setDetail("User not found");
                return new ResponseEntity<>(userNotFound, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            Error notAuthorized = new Error();
            notAuthorized.setTimeStamp(LocalDate.now());
            notAuthorized.setCodigo(401);
            notAuthorized.setDetail("You are not authorized");
            return new ResponseEntity<>(notAuthorized, HttpStatus.UNAUTHORIZED);
        }

    }
}
