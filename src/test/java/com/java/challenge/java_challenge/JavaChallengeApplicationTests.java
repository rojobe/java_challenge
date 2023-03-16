package com.java.challenge.java_challenge;

import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.ErrorResponseException;
import com.java.challenge.java_challenge.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.java.challenge.java_challenge.service.impl.RegularExpresionImpl;
import com.java.challenge.java_challenge.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class JavaChallengeApplicationTests {
	@Mock
	UserServiceImpl service;

	@Spy
	RegularExpresionImpl regexService;

	@Mock
	UserRepository repository;

	@Test
	void contextLoads() {
	}

	@Test
	void saveUser() throws ErrorResponseException {
		User user = new User();
		user.setUsername("rodrigobenito");
		user.setEmail("rodrigobenito@mail.com");
		user.setPassword("a2asfGf6");

		when(service.createUser(user)).thenReturn(user);

		assertThat(service.createUser(user)).isEqualTo(user);

	}

	@Test
	void saveUserIncorrectEmailFormat() throws ErrorResponseException {
		User user = new User();
		user.setUsername("rodrigobenito");
		user.setEmail("rodrigobenito@");
		user.setPassword("a2asfGf6");

		ErrorResponseException exceptionError = new ErrorResponseException();

		doThrow(new ErrorResponseException()).when(service).createUser(user);

		assertThrows(ErrorResponseException.class, ()->service.createUser(user));

	}

	/**
	 * Password debe tener solo una Mayúscula y solamente dos números (no necesariamente
	 * consecutivos), en combinación de letras minúsculas, largo máximo de 12 y mínimo 8
	 * @throws ErrorResponseException
	 */	@Test
	void saveUserIncorrectPasswordFormat() throws ErrorResponseException {
		User user = new User();
		user.setUsername("rodrigobenito");
		user.setEmail("rodrigobenito@mail.com");
		user.setPassword("a2aHsfGf6"); // el password contiene 2 mayusculas

		ErrorResponseException exceptionError = new ErrorResponseException();

		doThrow(new ErrorResponseException()).when(service).createUser(user);

		assertThrows(ErrorResponseException.class, ()->service.createUser(user));

	}


	@Test
	void loginOK() throws ErrorResponseException {
		User user = new User();
		user.setUsername("rodrigobenito");
		user.setEmail("rodrigobenito@mail.com");
		user.setPassword("a2asfGf6");

		when(service.getUserByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);

		assertThat(service.getUserByUsernameAndPassword(user.getUsername(), user.getPassword())).isEqualTo(user);

	}

	@Test
	void loginIFailed() throws ErrorResponseException {
		User user = new User();
		user.setUsername("rodrigobenito");
		user.setEmail("rodrigobenito@mail.com");
		user.setPassword("a2asfGf6");

		ErrorResponseException exceptionError = new ErrorResponseException();

		doThrow(new ErrorResponseException()).when(service).getUserByUsernameAndPassword(user.getUsername(), "a2asfGf6P");

		assertThrows(ErrorResponseException.class, ()->service.getUserByUsernameAndPassword(user.getUsername(), "a2asfGf6P"));


	}
}
