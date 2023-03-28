package com.java.challenge.java_challenge.service.impl;

import com.java.challenge.java_challenge.dto.UserDTO;
import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.InvalidAccessException;
import com.java.challenge.java_challenge.error.RepositoryException;
import com.java.challenge.java_challenge.repository.UserRepository;
import com.java.challenge.java_challenge.service.RegularExpresionService;
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
    private final RegularExpresionService regularExpresionService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    //private final ExceptionMessageConfig exceptionMessageConfig;
    private final ModelMapper mapper;

    public UserServiceImpl (UserRepository userRepository,
                            RegularExpresionService regularExpresionService,
                            JwtTokenUtil jwtTokenUtil,
                            PasswordEncoder passwordEncoder,
                            ModelMapper mapper){
        this.userRepository = userRepository;
        this.regularExpresionService = regularExpresionService;
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



    /*
    @Override
    public UserDTO getUserByUsernameAndPassword(String username, String password) throws RepositoryException {
        List<ErrorMessage> errorMessageList = new ArrayList<>();
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);

        if(user == null){
            ErrorMessage userNotFoundErrorMessage = new ErrorMessage();
            userNotFoundErrorMessage.setTimeStamp(LocalDate.now());
            userNotFoundErrorMessage.setCodigo("204");
            userNotFoundErrorMessage.setDetailMessage("UserName or Password is Invalid");
            errorMessageList.add(userNotFoundErrorMessage);
        }

        if(!errorMessageList.isEmpty()){
            RepositoryException errorResponseTO = new RepositoryException();
            errorResponseTO.setErrorList(errorMessageList);
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

    */





}
