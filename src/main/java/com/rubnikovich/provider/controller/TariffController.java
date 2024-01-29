package com.rubnikovich.provider.controller;

import com.rubnikovich.provider.model.TariffPlan;
import com.rubnikovich.provider.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tariffPlan")
public class TariffController {

    private final TariffService tariffService;

    @Autowired
    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping()
    public String indexPlan(Model model) {
        List<TariffPlan> tariff = tariffService.findAll();
        model.addAttribute("tariffPlans", tariff);
        return "tariff/indexTariffPlan";  //отправляем на view
    }

    @GetMapping("/{id}")
    public String showPlan(@PathVariable("id") int id, Model model) {
        TariffPlan tariffPlan = tariffService.findOne(id);
        model.addAttribute("tariffPlan", tariffPlan);
        return "tariff/show";
    }
}
