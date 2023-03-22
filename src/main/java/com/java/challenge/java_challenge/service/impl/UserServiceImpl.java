package com.java.challenge.java_challenge.service.impl;

import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.ErrorResponseException;
import com.java.challenge.java_challenge.repository.UserRepository;
import com.java.challenge.java_challenge.service.RegularExpresionService;
import com.java.challenge.java_challenge.service.UserService;
import com.java.challenge.java_challenge.error.Error;
import com.java.challenge.java_challenge.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RegularExpresionService regularExpresionService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserServiceImpl (UserRepository userRepository, RegularExpresionService regularExpresionService, JwtTokenUtil jwtTokenUtil){
        this.userRepository = userRepository;
        this.regularExpresionService = regularExpresionService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    public User createUser(User user) throws ErrorResponseException {
        List<Error> errorList = new ArrayList<>();

        if(userRepository.findByEmail(user.getEmail()) != null){
            Error emailAlreadyExist = new Error();
            emailAlreadyExist.setTimeStamp(LocalDate.now());
            emailAlreadyExist.setCodigo(409);
            emailAlreadyExist.setDetail("Email already registered");
            errorList.add(emailAlreadyExist);
            ErrorResponseException errorResponseTO = new ErrorResponseException();
            errorResponseTO.setErrorList(errorList);
            throw errorResponseTO;
        }

        if(user.getPassword() == null || user.getPassword().isEmpty()){
            Error emptyPasswordError = new Error();
            emptyPasswordError.setTimeStamp(LocalDate.now());
            emptyPasswordError.setCodigo(500);
            emptyPasswordError.setDetail("Password can not be empty");
            errorList.add(emptyPasswordError);
        }else if(!regularExpresionService.regexPassword(user.getPassword())){
            Error regexPasswordError = new Error();
            regexPasswordError.setTimeStamp(LocalDate.now());
            regexPasswordError.setCodigo(500);
            regexPasswordError.setDetail("Incorrect Password format");
            errorList.add(regexPasswordError);
        }

        if(user.getEmail() == null || user.getEmail().isEmpty()){
            Error emptyEmailerror = new Error();
            emptyEmailerror.setTimeStamp(LocalDate.now());
            emptyEmailerror.setCodigo(500);
            emptyEmailerror.setDetail("Email can not be empty");
            errorList.add(emptyEmailerror);
        }else if(!regularExpresionService.regexEmail(user.getEmail())){
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

        user.setCreated(LocalDate.now());
        user.setLastLogin(LocalDate.now());
        user.setActive(true);
        User userResult = userRepository.save(user);
        String token = jwtTokenUtil.generateToken(user.getEmail());
        userResult.setToken(token);
        userResult.setId(userResult.getId());
        return userResult;

    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws ErrorResponseException {
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

        user.setCreated(LocalDate.now());
        user.setLastLogin(LocalDate.now());
        user.setActive(true);
        String token = jwtTokenUtil.generateToken(user.getEmail());
        user.setToken(token);

        return user;
    }

    @Override
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username);

    }

    @Override
    public User getUserByEmail(String email) throws ErrorResponseException {
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
        user.setToken(token);
        return user;
    }


}
