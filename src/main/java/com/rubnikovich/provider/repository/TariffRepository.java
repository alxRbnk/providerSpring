package com.rubnikovich.provider.repository;

import com.rubnikovich.provider.model.TariffPlan;
import com.rubnikovich.provider.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TariffRepository extends JpaRepository<TariffPlan, Integer> {

    List<User> findByPlanName(String name);


}
