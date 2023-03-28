package com.java.challenge.java_challenge;

import com.java.challenge.java_challenge.dto.UserDTO;
import com.java.challenge.java_challenge.entity.User;
import com.java.challenge.java_challenge.error.RepositoryException;
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
	void saveUser(){
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("rodrigobenito");
		userDTO.setEmail("rodrigobenito@mail.com");
		userDTO.setPassword("a2asfGf6");

		when(service.createUser(userDTO)).thenReturn(userDTO);

		assertThat(service.createUser(userDTO)).isEqualTo(userDTO);

	}



	@Test
	void saveUserIncorrectEmailFormat() throws RepositoryException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("rodrigobenito");
		userDTO.setEmail("rodrigobenito@");
		userDTO.setPassword("a2asfGf6");

		RepositoryException exceptionError = new RepositoryException();

		doThrow(new RepositoryException()).when(service).createUser(userDTO);

		assertThrows(RepositoryException.class, ()->service.createUser(userDTO));

	}

	/**
	 * Password debe tener solo una Mayúscula y solamente dos números (no necesariamente
	 * consecutivos), en combinación de letras minúsculas, largo máximo de 12 y mínimo 8
	 * @throws RepositoryException
	 */
	@Test
	void saveUserIncorrectPasswordFormat() throws RepositoryException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("rodrigobenito");
		userDTO.setEmail("rodrigobenito@mail.com");
		userDTO.setPassword("a2aHsfGf6"); // el password contiene 2 mayusculas

		RepositoryException exceptionError = new RepositoryException();

		doThrow(new RepositoryException()).when(service).createUser(userDTO);

		assertThrows(RepositoryException.class, ()->service.createUser(userDTO));

	}



	@Test
	void loginOK() throws RepositoryException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("rodrigobenito");
		userDTO.setEmail("rodrigobenito@mail.com");
		userDTO.setPassword("a2asfGf6");

		when(service.getUserByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword())).thenReturn(userDTO);

		assertThat(service.getUserByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword())).isEqualTo(userDTO);

	}

	@Test
	void loginIFailed() throws RepositoryException {
		User user = new User();
		user.setUsername("rodrigobenito");
		user.setEmail("rodrigobenito@mail.com");
		user.setPassword("a2asfGf6");

		RepositoryException exceptionError = new RepositoryException();

		doThrow(new RepositoryException()).when(service).getUserByUsernameAndPassword(user.getUsername(), "a2asfGf6P");

		assertThrows(RepositoryException.class, ()->service.getUserByUsernameAndPassword(user.getUsername(), "a2asfGf6P"));


	}

	 */


}
