package com.rubnikovich.provider.controller;

import com.rubnikovich.provider.exception.CustomException;
import com.rubnikovich.provider.model.Operation;
import com.rubnikovich.provider.model.TariffPlan;
import com.rubnikovich.provider.model.User;
import com.rubnikovich.provider.service.OperationService;
import com.rubnikovich.provider.service.TariffService;
import com.rubnikovich.provider.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/operation")
public class OperationController {

    private final OperationService operationService;
    private final UsersService usersService;
    private final TariffService tariffService;

    @Autowired
    public OperationController(OperationService operationService, UsersService usersService, TariffService tariffService) {
        this.operationService = operationService;
        this.usersService = usersService;
        this.tariffService = tariffService;
    }

    @GetMapping()
    public String indexOperation(Model model) {
        List<Operation> operations = operationService.findAll();
        model.addAttribute("userOperations", operations);
        return "/operation/indexOperation";  //отправляем на view
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("operation", operationService.findOne(id));
        return "operation/showOperation";
    }

    @PostMapping("/addPlan")
    public String addPlan(Principal principal, @RequestParam("tariffPlanId") int tariffPlanId) {
        User currentUser = usersService.findByLogin(principal.getName());
        TariffPlan tariffPlan = tariffService.findOne(tariffPlanId);
        try {
            operationService.createOperation(currentUser, tariffPlan);
        } catch (CustomException e) {
            return "redirect:/user";//new page
        }
        return "redirect:/";
    }

}
