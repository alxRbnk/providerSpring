package com.rubnikovich.provider.controller;


import com.rubnikovich.provider.exception.CustomException;
import com.rubnikovich.provider.mail.EmailSender;
import com.rubnikovich.provider.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import com.rubnikovich.provider.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UsersController {

    private final UsersService userService;

    @Autowired
    public UsersController(UsersService peopleService) {
        this.userService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "user/show";
    }

    @GetMapping("/userPage")
    public String userPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByLogin(username);
        model.addAttribute("user", user);
        return "user/page";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "user/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "user/new";
        }
        userService.save(user);
        authenticateUserAndSetSession(user, request);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findOne(id));
        return "user/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        userService.update(id, user);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, Authentication authentication) {
        userService.delete(id);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/";
    }

    @PostMapping("/topUp/{id}")
    public String addBalance(Model model, @PathVariable("id") int id, @RequestParam("amount") BigDecimal amount) {
        try {
            userService.addBalance(id, amount);
        } catch (CustomException e) {
            return "redirect:/user/userPage";
        }
        return "redirect:/user/userPage";
    }

    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        String login = user.getLogin();
        String password = user.getPassword();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(login, password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }
}
