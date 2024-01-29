package com.rubnikovich.provider.service;

import com.rubnikovich.provider.model.Operation;
import com.rubnikovich.provider.model.TariffPlan;
import com.rubnikovich.provider.repository.OperationRepository;
import com.rubnikovich.provider.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

}
