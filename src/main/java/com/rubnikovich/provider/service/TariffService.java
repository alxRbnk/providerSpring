package com.rubnikovich.provider.service;

import com.rubnikovich.provider.model.Role;
import com.rubnikovich.provider.model.TariffPlan;
import com.rubnikovich.provider.model.User;
import com.rubnikovich.provider.repository.TariffRepository;
import com.rubnikovich.provider.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TariffService {

        private final TariffRepository tariffRepository;

        @Autowired
        public TariffService(TariffRepository tariffRepository) {
            this.tariffRepository = tariffRepository;
        }

        public List<TariffPlan> findAll() {
            return tariffRepository.findAll();
        }

        public TariffPlan findOne(int id) {
            Optional<TariffPlan> foundTariffPlan = tariffRepository.findById(id);
            return foundTariffPlan.orElse(null); //some message
        }

}
