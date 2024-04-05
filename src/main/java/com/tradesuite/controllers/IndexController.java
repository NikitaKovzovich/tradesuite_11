package com.tradesuite.controllers;

import com.tradesuite.controllers.main.Main;
import com.tradesuite.model.AppUser;
import com.tradesuite.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController extends Main {
    @GetMapping
    public String index2(Model model) {
        getCurrentUserAndRole(model);
        AppUser user = getUser();
        if (user.getRole() == Role.ADMIN) {
            model.addAttribute("navName", "Кабинет менеджера");
        } else if (user.getRole() == Role.MANAGER) {
            model.addAttribute("navName", "Кабинет сотрудника");
        } else if (user.getRole() == Role.USER) {
            model.addAttribute("navName", "Кабинет клиента");
        }
        return "index";
    }
}