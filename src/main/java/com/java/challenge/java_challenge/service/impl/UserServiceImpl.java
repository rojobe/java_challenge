package com.java.challenge.java_challenge.service.impl;

import com.java.challenge.java_challenge.dto.UserDTO;
import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.Error;
import com.java.challenge.java_challenge.error.ErrorResponseException;
import com.java.challenge.java_challenge.repository.UserRepository;
import com.java.challenge.java_challenge.service.RegularExpresionService;
import com.java.challenge.java_challenge.service.UserService;
import com.java.challenge.java_challenge.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RegularExpresionService regularExpresionService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl (UserRepository userRepository,
                            RegularExpresionService regularExpresionService,
                            JwtTokenUtil jwtTokenUtil,
                            PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.regularExpresionService = regularExpresionService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) throws ErrorResponseException {
        List<Error> errorList = new ArrayList<>();

        if(userRepository.findByEmail(userDTO.getEmail()) != null){
            Error emailAlreadyExist = new Error();
            emailAlreadyExist.setTimeStamp(LocalDate.now());
            emailAlreadyExist.setCodigo(409);
            emailAlreadyExist.setDetail("Email already registered");
            errorList.add(emailAlreadyExist);
            ErrorResponseException errorResponseTO = new ErrorResponseException();
            errorResponseTO.setErrorList(errorList);
            throw errorResponseTO;
        }

        if(userDTO.getPassword() == null || userDTO.getPassword().isEmpty()){
            Error emptyPasswordError = new Error();
            emptyPasswordError.setTimeStamp(LocalDate.now());
            emptyPasswordError.setCodigo(500);
            emptyPasswordError.setDetail("Password can not be empty");
            errorList.add(emptyPasswordError);
        }else if(!regularExpresionService.regexPassword(userDTO.getPassword())){
            Error regexPasswordError = new Error();
            regexPasswordError.setTimeStamp(LocalDate.now());
            regexPasswordError.setCodigo(500);
            regexPasswordError.setDetail("Incorrect Password format");
            errorList.add(regexPasswordError);
        }

        if(userDTO.getEmail() == null || userDTO.getEmail().isEmpty()){
            Error emptyEmailerror = new Error();
            emptyEmailerror.setTimeStamp(LocalDate.now());
            emptyEmailerror.setCodigo(500);
            emptyEmailerror.setDetail("Email can not be empty");
            errorList.add(emptyEmailerror);
        }else if(!regularExpresionService.regexEmail(userDTO.getEmail())){
            Error regexEmailerror = new Error();
            regexEmailerror.setTimeStamp(LocalDate.now());
            regexEmailerror.setCodigo(500);
            regexEmailerror.setDetail("Incorrect Email format");
            errorList.add(regexEmailerror);
        }

        if(!errorList.isEmpty()){
            ErrorResponseException errorResponseTO = new ErrorResponseException();
            errorResponseTO.setErrorList(errorList);
            throw errorResponseTO;
        }
        userDTO.setActive(true);
        userDTO.setCreated(LocalDate.now());
        userDTO.setLastLogin(LocalDate.now());
        User user = new User();
        user.setCreated(LocalDate.now());
        user.setLastLogin(LocalDate.now());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPhones(userDTO.getPhones());
        userRepository.save(user);
        String token = jwtTokenUtil.generateToken(userDTO.getEmail());
        userDTO.setToken(token);
        userDTO.setId(user.getId());
        return userDTO;

    }

    @Override
    public UserDTO getUserByUsernameAndPassword(String username, String password) throws ErrorResponseException {
        List<Error> errorList = new ArrayList<>();
        User user = userRepository.findByUsernameAndPassword(username, password);
        if(user == null){
            Error userNotFoundError = new Error();
            userNotFoundError.setTimeStamp(LocalDate.now());
            userNotFoundError.setCodigo(204);
            userNotFoundError.setDetail("UserName or Password is Invalid");
            errorList.add(userNotFoundError);
        }

        if(!errorList.isEmpty()){
            ErrorResponseException errorResponseTO = new ErrorResponseException();
            errorResponseTO.setErrorList(errorList);
            throw errorResponseTO;
        }

        String token = jwtTokenUtil.generateToken(user.getEmail());
        UserDTO userDTO = new UserDTO();
        userDTO.setToken(token);
        userDTO.setActive(user.isActive());
        userDTO.setCreated(user.getCreated());
        userDTO.setPhones(user.getPhones());
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setLastLogin(user.getLastLogin());

        return userDTO;
    }

    @Override
    public UserDTO getUserByEmail(String email) throws ErrorResponseException {
        List<Error> errorList = new ArrayList<>();
        User user = userRepository.findByEmail(email);
        if(user == null){
            Error userNotFoundError = new Error();
            userNotFoundError.setTimeStamp(LocalDate.now());
            userNotFoundError.setCodigo(204);
            userNotFoundError.setDetail("User not found");
            errorList.add(userNotFoundError);
        }

        if(!errorList.isEmpty()){
            ErrorResponseException errorResponseTO = new ErrorResponseException();
            errorResponseTO.setErrorList(errorList);
            throw errorResponseTO;
        }
        String token = jwtTokenUtil.generateToken(user.getEmail());
        UserDTO userDTO = new UserDTO();
        userDTO.setToken(token);
        userDTO.setUsername(user.getUsername());
        userDTO.setActive(user.isActive());
        userDTO.setCreated(user.getCreated());
        userDTO.setPhones(user.getPhones());
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setLastLogin(user.getLastLogin());

        return userDTO;
    }

    @Override
    public UserDTO login(String email, String password) throws ErrorResponseException {
        UserDTO userData = getUserByEmail(email);
        if(userData != null && passwordEncoder.matches(password, userData.getPassword())){
            return userData;
        }
        List<Error> errorList = new ArrayList<>();
        Error incorrectPassword = new Error();
        incorrectPassword.setTimeStamp(LocalDate.now());
        incorrectPassword.setCodigo(409);
        incorrectPassword.setDetail("Password does not match");
        errorList.add(incorrectPassword);
        ErrorResponseException errorResponseTO = new ErrorResponseException();
        errorResponseTO.setErrorList(errorList);
        throw errorResponseTO;
    }


}
