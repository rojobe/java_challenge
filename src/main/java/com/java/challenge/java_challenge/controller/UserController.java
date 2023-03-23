package com.java.challenge.java_challenge.controller;

import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.Error;
import com.java.challenge.java_challenge.error.ErrorResponseException;
import com.java.challenge.java_challenge.service.UserService;
import com.java.challenge.java_challenge.to.SearchTO;
import com.java.challenge.java_challenge.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class UserController {

    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(UserService userService, JwtTokenUtil jwtTokenUtil){
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<Object> signUpUser(@RequestBody User user){
        try{
            User userResult = userService.createUser(user);
            return ResponseEntity.ok(userResult);
        }catch(ErrorResponseException errorResponseException){
            return new ResponseEntity<>(errorResponseException.getErrorList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> loginUser(@RequestHeader String token) {
        try {

            User userData = userService.getUserByEmail(jwtTokenUtil.getEmailFromToken(token));

            return new ResponseEntity<>(userData, HttpStatus.OK);

        } catch (ErrorResponseException errorResponseException) {

            return new ResponseEntity<>(errorResponseException.getErrorList(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchUser(@RequestBody SearchTO searchTO, @RequestHeader String token) {
        try {
            if (searchTO.getEmail().equalsIgnoreCase(jwtTokenUtil.getEmailFromToken(token))) {
                return ResponseEntity.ok(userService.getUserByEmail(searchTO.getEmail()));
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
