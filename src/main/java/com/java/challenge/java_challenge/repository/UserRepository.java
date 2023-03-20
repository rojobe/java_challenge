package com.java.challenge.java_challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.java.challenge.java_challenge.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

}
