package com.tradesuite.controllers;

import com.tradesuite.controllers.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/history")
public class HistoryController extends Main {
    @GetMapping
    public String history(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("applications", getUser().getApplications());
        return "history";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        applicationRepo.deleteById(id);
        return "redirect:/history";
    }
}