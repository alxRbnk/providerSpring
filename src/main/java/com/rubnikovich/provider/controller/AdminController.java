package com.rubnikovich.provider.controller;

import com.rubnikovich.provider.model.Role;
import com.rubnikovich.provider.model.User;
import com.rubnikovich.provider.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsersService usersService;

    @Autowired
    public AdminController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public String adminPage(Model model, @ModelAttribute("user") User user) {
        model.addAttribute("users", usersService.findAll());
        return "admin/adminPage";
    }

    @PostMapping("/add")
    public String makeAdmin(@ModelAttribute("user") User user) {
        usersService.updateRole(user.getId(), Role.ADMIN);
        return "redirect:/user";
    }
//    @PostMapping("/add")
//    public String makeClient(@ModelAttribute("user") User user) {
//        usersService.updateRole(user.getId(), Role.CLIENT);
//        return "redirect:/user";
//    }
//    @PostMapping("/add")
//    public String makeGuest(@ModelAttribute("user") User user) {
//        usersService.updateRole(user.getId(), Role.GUEST);
//        return "redirect:/user";
//    }
}
