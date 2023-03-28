package com.java.challenge.java_challenge;

import com.java.challenge.java_challenge.dto.UserDTO;
import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.IncorrectFormatException;
import com.java.challenge.java_challenge.error.RepositoryException;
import com.java.challenge.java_challenge.repository.UserRepository;

import static com.java.challenge.java_challenge.utils.UserUtils.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.java.challenge.java_challenge.service.impl.RegularExpresionImpl;
import com.java.challenge.java_challenge.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;


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
	void saveUser(){
		UserDTO userDTO = getUserDTO();

		when(service.createUser(userDTO)).thenReturn(userDTO);

		assertThat(service.createUser(userDTO)).isEqualTo(userDTO);

	}

	@Test
	void saveUserIncorrectEmailFormat() throws RepositoryException {
		UserDTO userDTO = getUserDTOInvalidEmail();

		doThrow(new IncorrectFormatException(
				HttpStatus.BAD_REQUEST.toString(),
				"Email has an incorrect format",
				HttpStatus.CONFLICT)).when(service).createUser(userDTO);

		assertThrows(IncorrectFormatException.class, ()->service.createUser(userDTO));

	}

	/**
	 * Password debe tener solo una Mayúscula y solamente dos números (no necesariamente
	 * consecutivos), en combinación de letras minúsculas, largo máximo de 12 y mínimo 8
	 * @throws RepositoryException
	 */
	@Test
	void saveUserIncorrectPasswordFormat() throws RepositoryException {
		UserDTO userDTO = getUserDTOInvalidFormatPassword();

		doThrow(new IncorrectFormatException(
				HttpStatus.BAD_REQUEST.toString(),
				"Password has an incorrect format",
				HttpStatus.CONFLICT)).when(service).createUser(userDTO);

		assertThrows(IncorrectFormatException.class, ()->service.createUser(userDTO));

	}

	@Test
	void loginOK() throws RepositoryException {
		UserDTO userDTO = getUserDTO();

		when(service.login(userDTO.getEmail(), userDTO.getPassword())).thenReturn(userDTO);

		assertThat(service.login(userDTO.getEmail(), userDTO.getPassword())).isEqualTo(userDTO);

	}

	@Test
	void loginIFailed() throws RepositoryException {
		UserDTO userDTO = getUserDTO();

		doThrow(new IncorrectFormatException(
				HttpStatus.CONFLICT.toString(),
				"Incorrect Password",
				HttpStatus.CONFLICT)).when(service).login(userDTO.getUsername(), "a2asfGf6P");

		assertThrows(IncorrectFormatException.class, ()->service.login(userDTO.getUsername(), "a2asfGf6P"));


	}




}
