package com.rubnikovich.provider.repository;

import com.rubnikovich.provider.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);

    List<User> findByFirstName(String name);

    List<User> findByFirstNameOrderByLastName(String name);

    List<User> findByEmail(String email);

    List<User> findByFirstNameStartingWith(String startWith);

    List<User> findByFirstNameOrEmail(String name, String email);

}
