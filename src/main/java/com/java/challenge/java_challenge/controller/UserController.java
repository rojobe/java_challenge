package com.java.challenge.java_challenge.controller;

import com.java.challenge.java_challenge.dto.UserDTO;
import com.java.challenge.java_challenge.service.UserService;
import com.java.challenge.java_challenge.util.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/v1/user")
@RestController
public class UserController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public UserController(UserService userService, JwtTokenUtil jwtTokenUtil){
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @ApiOperation("Operacion registrar un usuario en el sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuario Registrado",response = UserDTO.class)
    })
    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> signUpUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @ApiOperation("Operacion que loguear a un usuario a traves de su email y password")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Informacion de Usuario",response = UserDTO.class)
    })
    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> loginUserWithPassword(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.login(userDTO.getEmail(), userDTO.getPassword()));
    }

    @ApiOperation("Operacion que permite loguear un usuario a traves de un token")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Informacion de Usuario",response = UserDTO.class)
    })
    @GetMapping(value = "/login-by-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> loginUser(@RequestHeader String token) {
        return ResponseEntity.ok(userService.getUserByEmail(jwtTokenUtil.getEmailFromToken(token)));
    }

}
