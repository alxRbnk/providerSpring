package com.rubnikovich.provider.repository;

import com.rubnikovich.provider.model.Operation;
import com.rubnikovich.provider.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Integer> {

}
