package com.tradesuite.controllers;

import com.tradesuite.controllers.main.Main;
import com.tradesuite.model.AppUser;
import com.tradesuite.model.enums.Department;
import com.tradesuite.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController extends Main {
    @GetMapping
    public String users(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", Role.values());
        model.addAttribute("departments", Department.values());
        return "users";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @RequestParam Role role, @RequestParam(defaultValue = "ASSEMBLING") Department department) {
        AppUser user = userRepo.getReferenceById(id);
        user.setRole(role);
        user.setDepartment(department);
        userRepo.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "redirect:/users";
    }
}