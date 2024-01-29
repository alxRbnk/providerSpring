package com.rubnikovich.provider.controller;

import com.rubnikovich.provider.model.TariffPlan;
import com.rubnikovich.provider.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final TariffService tariffService;

    @Autowired
    public HomeController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping("/")
    public String hello(Model model) {
        List<TariffPlan> tariff = tariffService.findAll();
        model.addAttribute("tariffPlans", tariff);
        return "index";
    }

}
