package com.java.challenge.java_challenge.controller;

import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.Error;
import com.java.challenge.java_challenge.error.ErrorResponseException;
import com.java.challenge.java_challenge.service.UserService;
import com.java.challenge.java_challenge.to.SignUpResponseTO;
import com.java.challenge.java_challenge.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<Object> signUpUser(@RequestBody User user){
        try{
            User userResult = userService.createUser(user);
             return ResponseEntity.ok(userResult);
        }catch(ErrorResponseException errorResponseException){
            return new ResponseEntity<>(errorResponseException.getErrorList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {

            User userData = userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());

            return new ResponseEntity<>(userData, HttpStatus.OK);

        } catch (ErrorResponseException errorResponseException) {

            return new ResponseEntity<>(errorResponseException.getErrorList(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("username") String username, @RequestHeader String token) {
        try {
            if (username.equalsIgnoreCase(jwtTokenUtil.getUsernameFromToken(token))) {
                return ResponseEntity.ok(userService.getUser(username));
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
