package com.tradesuite.controllers;

import com.tradesuite.model.AppUser;
import com.tradesuite.model.Application;
import com.tradesuite.model.enums.ApplicationStatus;
import com.tradesuite.model.enums.Department;
import com.tradesuite.model.enums.Role;
import com.tradesuite.repo.ApplicationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ApplicationControllerTest {

    @Mock
    private ApplicationRepo applicationRepo;

    @InjectMocks
    private ApplicationController applicationController;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApplications_Admin() {
        AppUser user = new AppUser();
        user.setRole(Role.valueOf("ADMIN"));

        List<Application> applications = new ArrayList<>();
        when(applicationRepo.findAll(Sort.by("id"))).thenReturn(applications);

        String viewName = applicationController.applications(model);
        assertEquals("applications", viewName);

        verify(model).addAttribute("applications", applications);
        verify(model).addAttribute("message", "Нет заявок для обработки");
    }

    @Test
    void testApplications_Manager_Assembling() {
        AppUser user = new AppUser();
        user.setRole(Role.valueOf("MANAGER"));
        user.setDepartment(Department.valueOf("ASSEMBLING"));

        List<Application> applications = new ArrayList<>();
        when(applicationRepo.findAllByStatus(ApplicationStatus.ASSEMBLY)).thenReturn(applications);

        String viewName = applicationController.applications(model);
        assertEquals("applications", viewName);

        verify(model).addAttribute("applications", applications);
        verify(model, never()).addAttribute("message", "Нет заявок для обработки");
    }

    @Test
    void testApplications_Manager_Packaging() {
        AppUser user = new AppUser();
        user.setRole(Role.valueOf("MANAGER"));
        user.setDepartment(Department.valueOf("PACKAGING"));

        List<Application> applications = new ArrayList<>();
        when(applicationRepo.findAllByStatus(ApplicationStatus.PACKED)).thenReturn(applications);

        String viewName = applicationController.applications(model);
        assertEquals("applications", viewName);

        verify(model).addAttribute("applications", applications);
        verify(model, never()).addAttribute("message", "Нет заявок для обработки");
    }

    @Test
    void testApplications_Manager_Delivery() {
        AppUser user = new AppUser();
        user.setRole(Role.valueOf("MANAGER"));
        user.setDepartment(Department.valueOf("DELIVERY"));

        List<Application> applications = new ArrayList<>();
        when(applicationRepo.findAllByStatus(ApplicationStatus.IN_DELIVERY)).thenReturn(applications);

        String viewName = applicationController.applications(model);
        assertEquals("applications", viewName);

        verify(model).addAttribute("applications", applications);
        verify(model, never()).addAttribute("message", "Нет заявок для обработки");
    }

    @Test
    void testAssembly() {
        Long id = 1L;
        Application application = new Application();
        when(applicationRepo.getReferenceById(id)).thenReturn(application);

        String viewName = applicationController.assembly(model, id);
        assertEquals("applications", viewName);

        assertEquals(ApplicationStatus.ASSEMBLY, application.getStatus());
        verify(applicationRepo).save(application);
        verify(model).addAttribute("message", "Заявка успешно подтверждена");
    }

    @Test
    void testPacked() {
        Long id = 1L;
        String redirectUrl = "redirect:/applications";
        Application application = new Application();
        application.setStatus(ApplicationStatus.PACKED);
        when(applicationRepo.getReferenceById(id)).thenReturn(application);

        String viewName = applicationController.packed(model, id);
        assertEquals(redirectUrl, viewName);

        verify(applicationRepo).save(application);
        verify(model).addAttribute("message", "Заявка успешно собрана");
    }

    @Test
    void testDelivery() {
        Long id = 1L;
        String redirectUrl = "redirect:/applications";
        Application application = new Application();
        application.setStatus(ApplicationStatus.IN_DELIVERY);
        when(applicationRepo.getReferenceById(id)).thenReturn(application);

        String viewName = applicationController.delivery(model, id);
        assertEquals(redirectUrl, viewName);

        verify(applicationRepo).save(application);
        verify(model).addAttribute("message", "Заявка успешно упакована");
    }

    @Test
    void testDelivered() {
        Long id = 1L;
        String redirectUrl = "redirect:/applications";
        Application application = new Application();
        application.setStatus(ApplicationStatus.DELIVERED);
        when(applicationRepo.getReferenceById(id)).thenReturn(application);

        String viewName = applicationController.delivered(model, id);
        assertEquals(redirectUrl, viewName);

        verify(applicationRepo).save(application);
        verify(model).addAttribute("message", "Заявка успешно доставлена");
    }
    @Test
    void testApplicationsEmptyList() {
        List<Application> emptyApplications = new ArrayList<>();
        when(applicationRepo.findAll(Sort.by("id"))).thenReturn(emptyApplications);
        AppUser user = new AppUser();
        when(applicationController.getUser()).thenReturn(user);

        String viewName = applicationController.applications(model);
        assertEquals("applications", viewName);

        verify(model).addAttribute("applications", emptyApplications);
        verify(model).addAttribute("message", "Нет заявок для обработки");
    }


}
