package com.java.challenge.java_challenge.service.impl;

import com.java.challenge.java_challenge.dto.UserDTO;
import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.IncorrectFormatException;
import com.java.challenge.java_challenge.error.InvalidAccessException;
import com.java.challenge.java_challenge.error.RepositoryException;
import com.java.challenge.java_challenge.repository.UserRepository;
import com.java.challenge.java_challenge.service.ValidatorService;
import com.java.challenge.java_challenge.service.UserService;
import com.java.challenge.java_challenge.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidatorService validatorService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public UserServiceImpl (UserRepository userRepository,
                            ValidatorService validatorService,
                            JwtTokenUtil jwtTokenUtil,
                            PasswordEncoder passwordEncoder,
                            ModelMapper mapper){
        this.userRepository = userRepository;
        this.validatorService = validatorService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) throws RepositoryException {

        Optional<User> userToSave = userRepository.findByEmail(userDTO.getEmail());

        if(userToSave.isPresent()){
            throw new RepositoryException(
                    HttpStatus.CONFLICT.toString(),
                    "Email already registered",
                    HttpStatus.CONFLICT
            );
        }
        if(!validatorService.regexEmail(userDTO.getEmail())){
            throw new IncorrectFormatException(
                    HttpStatus.BAD_REQUEST.toString(),
                    "Email has an incorrect format",
                    HttpStatus.CONFLICT
            );
        }

        if(!validatorService.regexPassword(userDTO.getPassword())){
            throw new IncorrectFormatException(
                    HttpStatus.BAD_REQUEST.toString(),
                    "Password has an incorrect format",
                    HttpStatus.CONFLICT
            );
        }

        userDTO.setActive(true);
        userDTO.setCreated(LocalDate.now());
        userDTO.setLastLogin(LocalDate.now());

        User user = mapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);

        String token = jwtTokenUtil.generateToken(userDTO.getEmail());
        userDTO.setToken(token);
        userDTO.setId(user.getId());

        return userDTO;

    }


    @Override
    public UserDTO login(String email, String password) throws InvalidAccessException {

        UserDTO userData = getUserByEmail(email);
        if(userData != null && passwordEncoder.matches(password, userData.getPassword())){
            return userData;
        }else{
            throw new InvalidAccessException(
                    HttpStatus.CONFLICT.toString(),
                    "Incorrect Password",
                    HttpStatus.CONFLICT);
        }
    }

    @Override
    public UserDTO getUserByEmail(String email) throws RepositoryException {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new RepositoryException(
                            HttpStatus.CONFLICT.toString(),
                            "Email not registered",
                            HttpStatus.CONFLICT
                    );
                }));


        UserDTO userDTO = mapper.map(user.get(), UserDTO.class);
        userDTO.setToken(jwtTokenUtil.generateToken(userDTO.getEmail()));

        return userDTO;
    }

}
