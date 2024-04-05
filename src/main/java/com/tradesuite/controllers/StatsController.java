package com.tradesuite.controllers;

import com.tradesuite.controllers.main.Main;
import com.tradesuite.model.Category;
import com.tradesuite.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/stats")
public class StatsController extends Main {
    @GetMapping
    public String stats(Model model) {
        getCurrentUserAndRole(model);

        List<Product> products = productsRepo.findAll();
        model.addAttribute("products", products);

        products.sort(Comparator.comparing(Product::getIncome));
        Collections.reverse(products);

        String[] incomeString = new String[5];
        float[] incomeFloat = new float[5];

        for (int i = 0; i < products.size(); i++) {
            if (i == 5) break;
            incomeString[i] = products.get(i).getName();
            incomeFloat[i] = products.get(i).getIncome();
        }

        model.addAttribute("incomeString", incomeString);
        model.addAttribute("incomeFloat", incomeFloat);

        List<Category> categories = categoryRepo.findAll();

        String[] categoryString = new String[categories.size()];
        float[] categoryFloat = new float[categories.size()];
        int[] categoryInt = new int[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            categoryString[i] = categories.get(i).getName();
            categoryFloat[i] = categories.get(i).getIncome();
            categoryInt[i] = categories.get(i).getIncomeQuantity();
        }

        model.addAttribute("categoryString", categoryString);
        model.addAttribute("categoryFloat", categoryFloat);
        model.addAttribute("categoryInt", categoryInt);

        return "stats";
    }
}
