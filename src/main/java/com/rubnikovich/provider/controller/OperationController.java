package com.rubnikovich.provider.controller;

import com.rubnikovich.provider.model.Operation;
import com.rubnikovich.provider.service.OperationService;
import com.rubnikovich.provider.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/operation")
public class OperationController {

    private final OperationService operationService;
    private final UsersService usersService;

    @Autowired
    public OperationController(OperationService operationService, UsersService usersService) {
        this.operationService = operationService;
        this.usersService = usersService;
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

}
