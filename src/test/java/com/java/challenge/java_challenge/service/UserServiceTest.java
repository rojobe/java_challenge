package com.java.challenge.java_challenge.service;

//import com.java.challenge.java_challenge.config.ExceptionMessageConfig;
import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.repository.UserRepository;
import com.java.challenge.java_challenge.service.impl.RegularExpresionImpl;
import com.java.challenge.java_challenge.service.impl.UserServiceImpl;
import com.java.challenge.java_challenge.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserRepository repository = mock(UserRepository.class);

    private RegularExpresionService regularExpresionService = new RegularExpresionImpl();

    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    private PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    //private ExceptionMessageConfig exceptionMessageConfig;

    private ModelMapper mapper;


    private UserService service = new UserServiceImpl(repository,
            regularExpresionService,
            jwtTokenUtil,
            bCryptPasswordEncoder,
            //exceptionMessageConfig,
            mapper);

    @Test
    void createUser() {
        User user = new User();
        user.setUsername("rodrigobenito");
        user.setEmail("rodrigobenito@mail.com");
        user.setPassword("a2asfGf6");

        when(repository.save(user)).thenReturn(user);

        assertThat(repository.save(user)).isEqualTo(user);

    }

    @Test
    void login() {

        User user = new User();
        user.setEmail("rodrigobenito@mail.com");
        when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Optional<User> userFound = repository.findByEmail(user.getEmail());
        assertThat(userFound.get()).isNotNull();
    }

    /*

    @Test
    void getUserByUsernameAndPassword() {
        User user = new User();
        user.setUsername("rodrigobenito");
        user.setEmail("rodrigobenito@mail.com");
        user.setPassword("a2asfGf6");

        when(repository.findByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);
        User userFound = repository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        assertThat(userFound).isEqualTo(user);
    }
*/
    @Test
    void getUserByEmail() {
        User user = new User();
        user.setUsername("rodrigobenito");
        user.setEmail("rodrigobenito@mail.com");
        user.setPassword("a2asfGf6");

        when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Optional<User> userFound = repository.findByEmail(user.getEmail());
        assertThat(userFound.get()).isEqualTo(user);

    }





}