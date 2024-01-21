package com.rubnikovich.provider.controller;

import com.rubnikovich.provider.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("tariffPlans", tariffService.findAll());
        return "tariff/indexTariffPlan";  //отправляем на view
    }

    @GetMapping("/{id}")
    public String showPlan(@PathVariable("id") int id, Model model) {
        model.addAttribute("tariffPlan", tariffService.findOne(id));
        return "tariff/show";
    }
}
