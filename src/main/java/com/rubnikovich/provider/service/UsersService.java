package com.rubnikovich.provider.service;

import com.rubnikovich.provider.model.Role;
import com.rubnikovich.provider.model.User;
import com.rubnikovich.provider.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {

    private final UserRepository usersRepository;

    @Autowired
    public UsersService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public User findOne(int id) {
        Optional<User> foundUser = usersRepository.findById(id);
        return foundUser.orElse(null); //some message
    }

    @Transactional
    public void save(User user) {
        user.setRole(Role.CLIENT);
        user.setPassword("pass");
        user.setIsBlocked(false);
        user.setBalance(new BigDecimal(1000));
        usersRepository.save(user);
    }

    @Transactional
    public void update(int id, User updatedUser) {
        updatedUser.setId(id);
        usersRepository.save(updatedUser);
    }

    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

    //-----------------------
    @Transactional
    public void updateRole(int id, Role role) {
        Optional<User> optionalUser = usersRepository.findById(id);
        optionalUser.ifPresent(user -> {
            user.setRole(role);
            usersRepository.save(user);
        });
    }

}
