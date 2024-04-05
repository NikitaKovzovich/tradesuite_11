package com.tradesuite.controllers;

import com.tradesuite.controllers.main.Main;
import com.tradesuite.model.Application;
import com.tradesuite.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController extends Main {
    @GetMapping
    public String products(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("products", productsRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
        return "products";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(defaultValue = "") String name, @RequestParam Long categoryId) {
        getCurrentUserAndRole(model);
        model.addAttribute("products", productsRepo.findAllByNameContainingAndCategory_Id(name, categoryId));
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);
        return "products";
    }

    @GetMapping("/{id}")
    public String product(Model model, @PathVariable Long id) {
        getCurrentUserAndRole(model);
        model.addAttribute("product", productsRepo.getReferenceById(id));
        return "product";
    }

    @PostMapping("/{id}/app")
    public String app(Model model, @PathVariable Long id, @RequestParam int quantity, @RequestParam String contact, @RequestParam String address) {
        applicationRepo.save(new Application(quantity, contact, address, productsRepo.getReferenceById(id), getUser()));
        model.addAttribute("message","Заявка успешно подана");
        getCurrentUserAndRole(model);
        model.addAttribute("product", productsRepo.getReferenceById(id));
        return "product";
    }

    @GetMapping("/add")
    public String add(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("categories", categoryRepo.findAll());
        return "product_add";
    }

    @PostMapping("/add")
    public String add(
            Model model, @RequestParam String name, @RequestParam String date, @RequestParam int term,
            @RequestParam String origin, @RequestParam String firm, @RequestParam float price,
            @RequestParam String description, @RequestParam Long categoryId, @RequestParam MultipartFile photo
    ) {
        Product product = new Product(name, date, term, origin, firm, price, description, categoryRepo.getReferenceById(categoryId));

        try {
            if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result = "product/" + uuidFile + "_" + photo.getOriginalFilename();
                photo.transferTo(new File(uploadImg + "/" + result));
                product.setPhoto(result);
            }
        } catch (IOException e) {
            model.addAttribute("message", "Некорректные данные!");
            getCurrentUserAndRole(model);
            model.addAttribute("categories", categoryRepo.findAll());
            return "product_add";
        }

        product = productsRepo.save(product);

        return "redirect:/products/" + product.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {
        getCurrentUserAndRole(model);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("product", productsRepo.getReferenceById(id));
        return "product_edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(
            Model model, @RequestParam String name, @RequestParam String date, @RequestParam int term,
            @RequestParam String origin, @RequestParam String firm, @RequestParam float price,
            @RequestParam String description, @RequestParam Long categoryId, @RequestParam MultipartFile photo,
            @PathVariable Long id
    ) {
        Product product = productsRepo.getReferenceById(id);
        product.set(name, date, term, origin, firm, price, description, categoryRepo.getReferenceById(categoryId));

        try {
            if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result = "product/" + uuidFile + "_" + photo.getOriginalFilename();
                photo.transferTo(new File(uploadImg + "/" + result));
                product.setPhoto(result);
            }
        } catch (IOException e) {
            model.addAttribute("message", "Некорректные данные!");
            getCurrentUserAndRole(model);
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("product", product);
            return "product_edit";
        }

        productsRepo.save(product);

        return "redirect:/products/{id}";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productsRepo.deleteById(id);
        return "redirect:/products";
    }
}
