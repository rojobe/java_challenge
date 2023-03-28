package com.java.challenge.java_challenge.controller;

import com.java.challenge.java_challenge.dto.UserDTO;
import com.java.challenge.java_challenge.service.UserService;
import com.java.challenge.java_challenge.util.JwtTokenUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<UserDTO> loginUserWithPassword(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.login(userDTO.getEmail(), userDTO.getPassword()));
    }

    @GetMapping(value = "/login-by-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> loginUser(@RequestHeader String token) {
        return ResponseEntity.ok(userService.getUserByEmail(jwtTokenUtil.getEmailFromToken(token)));
    }






    /*

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
