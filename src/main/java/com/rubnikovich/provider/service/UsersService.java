package com.rubnikovich.provider.service;

import com.rubnikovich.provider.exception.CustomException;
import com.rubnikovich.provider.mail.EmailSender;
import com.rubnikovich.provider.model.Role;
import com.rubnikovich.provider.model.User;
import com.rubnikovich.provider.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {

    private final UserRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UserRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public User findOne(int id) {
        Optional<User> foundUser = usersRepository.findById(id);
        return foundUser.orElse(null); //some message
    }

    public User findByLogin(String login){
        Optional<User> foundUser = usersRepository.findByLogin(login);
        return foundUser.orElse(null); //some message
    }

    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CLIENT);
        user.setEnabled(true);
        user.setBalance(new BigDecimal(0));
        usersRepository.save(user);
//        turn off antivirus
//        EmailSender mailSender = new EmailSender();
//        String mail = user.getEmail();
//        mailSender.sendMail(mail, "welcome to RBNK provider");
    }

    @Transactional
    public void update(int id, User updatedUser) {
        updatedUser.setId(id);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        updatedUser.setRole(Role.CLIENT);
        updatedUser.setEnabled(true);
        usersRepository.save(updatedUser);
    }

    @Transactional
    public void addBalance(int userId, BigDecimal amount) throws CustomException {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        BigDecimal currentBalance = user.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);
        if (amount.compareTo(BigDecimal.ZERO) < 0){
            throw new CustomException("shortage of money");
        }
        user.setBalance(newBalance);
        usersRepository.save(user);
    }

    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

    @Transactional
    public void updateRole(int id, Role role) {
        Optional<User> optionalUser = usersRepository.findById(id);
        optionalUser.ifPresent(user -> {
            user.setRole(role);
            usersRepository.save(user);
        });
    }

    @Transactional
    public void setBlock(int userId, Boolean enabled) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        user.setEnabled(enabled);
        usersRepository.save(user);
    }

}
