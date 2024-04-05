package com.tradesuite.controllers;

import com.tradesuite.controllers.main.Main;
import com.tradesuite.model.AppUser;
import com.tradesuite.model.Application;
import com.tradesuite.model.enums.ApplicationStatus;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController extends Main {
    @GetMapping
    public String applications(Model model) {
        getCurrentUserAndRole(model);
        List<Application> applications = new ArrayList<>();
        AppUser user = getUser();

        switch (user.getRole()) {
            case ADMIN -> applications = applicationRepo.findAll(Sort.by("id"));
            case MANAGER -> {
                switch (user.getDepartment()) {
                    case ASSEMBLING -> applications = applicationRepo.findAllByStatus(ApplicationStatus.ASSEMBLY);
                    case PACKAGING -> applications = applicationRepo.findAllByStatus(ApplicationStatus.PACKED);
                    case DELIVERY -> applications = applicationRepo.findAllByStatus(ApplicationStatus.IN_DELIVERY);
                }
            }
        }

        if (applications.isEmpty()) {
            model.addAttribute("message", "Нет заявок для обработки");
        }

        model.addAttribute("applications", applications);
        return "applications";
    }

    @GetMapping("/{id}/assembly")
    public String assembly(Model model, @PathVariable Long id) {
        Application application = applicationRepo.getReferenceById(id);
        application.setStatus(ApplicationStatus.ASSEMBLY);
        applicationRepo.save(application);

        getCurrentUserAndRole(model);
        List<Application> applications = new ArrayList<>();
        AppUser user = getUser();

        switch (user.getRole()) {
            case ADMIN -> applications = applicationRepo.findAll();
            case MANAGER -> {
                switch (user.getDepartment()) {
                    case ASSEMBLING -> applications = applicationRepo.findAllByStatus(ApplicationStatus.ASSEMBLY);
                    case PACKAGING -> applications = applicationRepo.findAllByStatus(ApplicationStatus.PACKED);
                    case DELIVERY -> applications = applicationRepo.findAllByStatus(ApplicationStatus.IN_DELIVERY);
                }
            }
        }

        model.addAttribute("applications", applications);
        model.addAttribute("message", "Заявка успешно подтверждена");
        return "applications";
    }

    @GetMapping("/{id}/packed")
    public String packed(Model model, @PathVariable Long id) {
        Application application = applicationRepo.getReferenceById(id);
        application.setStatus(ApplicationStatus.PACKED);
        applicationRepo.save(application);

        getCurrentUserAndRole(model);
        List<Application> applications = new ArrayList<>();
        AppUser user = getUser();

        switch (user.getRole()) {
            case ADMIN -> applications = applicationRepo.findAll();
            case MANAGER -> {
                switch (user.getDepartment()) {
                    case ASSEMBLING -> applications = applicationRepo.findAllByStatus(ApplicationStatus.ASSEMBLY);
                    case PACKAGING -> applications = applicationRepo.findAllByStatus(ApplicationStatus.PACKED);
                    case DELIVERY -> applications = applicationRepo.findAllByStatus(ApplicationStatus.IN_DELIVERY);
                }
            }
        }

        model.addAttribute("applications", applications);
        model.addAttribute("message", "Заявка успешно собрана");
        return "applications";
    }

    @GetMapping("/{id}/delivery")
    public String delivery(Model model, @PathVariable Long id) {
        Application application = applicationRepo.getReferenceById(id);
        application.setStatus(ApplicationStatus.IN_DELIVERY);
        applicationRepo.save(application);

        getCurrentUserAndRole(model);
        List<Application> applications = new ArrayList<>();
        AppUser user = getUser();

        switch (user.getRole()) {
            case ADMIN -> applications = applicationRepo.findAll();
            case MANAGER -> {
                switch (user.getDepartment()) {
                    case ASSEMBLING -> applications = applicationRepo.findAllByStatus(ApplicationStatus.ASSEMBLY);
                    case PACKAGING -> applications = applicationRepo.findAllByStatus(ApplicationStatus.PACKED);
                    case DELIVERY -> applications = applicationRepo.findAllByStatus(ApplicationStatus.IN_DELIVERY);
                }
            }
        }

        model.addAttribute("applications", applications);
        model.addAttribute("message", "Заявка успешно упакована");
        return "applications";
    }

    @GetMapping("/{id}/delivered")
    public String delivered(Model model, @PathVariable Long id) {
        Application application = applicationRepo.getReferenceById(id);
        application.setStatus(ApplicationStatus.DELIVERED);
        applicationRepo.save(application);

        getCurrentUserAndRole(model);
        List<Application> applications = new ArrayList<>();
        AppUser user = getUser();

        switch (user.getRole()) {
            case ADMIN -> applications = applicationRepo.findAll();
            case MANAGER -> {
                switch (user.getDepartment()) {
                    case ASSEMBLING -> applications = applicationRepo.findAllByStatus(ApplicationStatus.ASSEMBLY);
                    case PACKAGING -> applications = applicationRepo.findAllByStatus(ApplicationStatus.PACKED);
                    case DELIVERY -> applications = applicationRepo.findAllByStatus(ApplicationStatus.IN_DELIVERY);
                }
            }
        }

        model.addAttribute("applications", applications);
        model.addAttribute("message", "Заявка успешно доставлена");
        return "applications";
    }
}