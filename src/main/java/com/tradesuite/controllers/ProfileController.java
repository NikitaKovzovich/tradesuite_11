package com.tradesuite.controllers;

import com.tradesuite.controllers.main.Main;
import com.tradesuite.model.AppUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController extends Main {
    @GetMapping
    public String profile(Model model) {
        getCurrentUserAndRole(model);
        return "profile";
    }

    @PostMapping("/photo")
    public String photo(Model model, @RequestParam MultipartFile photo) {
        try {
            if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result = "user/" + uuidFile + "_" + photo.getOriginalFilename();
                photo.transferTo(new File(uploadImg + "/" + result));

                AppUser user = getUser();
                user.setPhoto(result);
                userRepo.save(user);
            }
        } catch (IOException e) {
            model.addAttribute("message", "Некорректные данные!");
            getCurrentUserAndRole(model);
            return "profile";
        }
        return "redirect:/profile";
    }

    @PostMapping("/fio")
    public String fio(@RequestParam String fio) {
        AppUser user = getUser();
        user.setFio(fio);
        userRepo.save(user);
        return "redirect:/profile";
    }
}