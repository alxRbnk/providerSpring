package com.rubnikovich.provider.controller;

import com.rubnikovich.provider.model.Role;
import com.rubnikovich.provider.model.User;
import com.rubnikovich.provider.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "admin/userList";
    }

    @PostMapping("/setRole")
    public String setRole(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        Role newRole = Role.valueOf(role);
        usersService.updateRole(user.getId(), newRole);
        return "redirect:/admin";
    }

    @PostMapping("/setBlock")
    public String setBlock(@ModelAttribute("user") User user, @RequestParam("enabled") Boolean enabled) {
        usersService.setBlock(user.getId(), enabled);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, Authentication authentication) {
        usersService.delete(id);
        if (authentication != null && !isAdmin(authentication)) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/admin";
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }

}
