package com.rubnikovich.provider.service;

import com.rubnikovich.provider.exception.CustomException;
import com.rubnikovich.provider.model.Operation;
import com.rubnikovich.provider.model.Role;
import com.rubnikovich.provider.model.TariffPlan;
import com.rubnikovich.provider.model.User;
import com.rubnikovich.provider.repository.OperationRepository;
import com.rubnikovich.provider.repository.UserRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OperationService {

    private final OperationRepository operationRepository;
    private final UserRepository userRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository, UserRepository userRepository) {
        this.operationRepository = operationRepository;
        this.userRepository = userRepository;
    }

    public List<Operation> findAll() {
        return operationRepository.findAll();
    }

    public Operation findOne(int id) {
        Optional<Operation> foundOperation = operationRepository.findById(id);
        return foundOperation.orElse(null); //some message
    }

    @Transactional
    public void createOperation(User user, TariffPlan tariffPlan) throws CustomException {
        BigDecimal currentBalance = user.getBalance();
        BigDecimal discountPercentage = tariffPlan.getDiscount();
        BigDecimal price = tariffPlan.getPrice();
        BigDecimal discountMoney = price.multiply(discountPercentage.divide(new BigDecimal(100)));
        BigDecimal finalPrice = price.subtract(discountMoney);
        BigDecimal balanceAfter = currentBalance.subtract(finalPrice);
        if (balanceAfter.compareTo(BigDecimal.ZERO) < 0){
            throw new CustomException("shortage of money");
        }
        user.setBalance(balanceAfter);
        Operation operation = new Operation();
        operation.setOwner(user);
        operation.setPlan(tariffPlan);
        operation.setStartTime(new Date());
        operation.setEndTime(new Date());
        operation.setFinalPrice(finalPrice);
        operationRepository.save(operation);
    }



}
