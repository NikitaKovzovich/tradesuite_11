package com.tradesuite.controllers;

import com.tradesuite.controllers.main.Main;
import com.tradesuite.model.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryController extends Main {
    @GetMapping
    public String category(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("categories", categoryRepo.findAll());
        return "category";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name) {
        categoryRepo.save(new Category(name));
        return "redirect:/category";
    }

    @PostMapping("/{id}/edit")
    public String edit(@RequestParam String name, @PathVariable Long id) {
        Category category = categoryRepo.getReferenceById(id);
        category.set(name);
        categoryRepo.save(category);
        return "redirect:/category";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        categoryRepo.deleteById(id);
        return "redirect:/category";
    }
}
