package com.tradesuite.controllers;
import com.tradesuite.model.AppUser;
import com.tradesuite.model.Application;
import com.tradesuite.model.enums.ApplicationStatus;
import com.tradesuite.repo.ApplicationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class ApplicationControllerTest {

    @Mock
    private ApplicationRepo applicationRepository;

    @InjectMocks
    private ApplicationController applicationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApplications() {
        Model model = mock(Model.class);
        AppUser user = new AppUser();
        when(applicationRepository.findAll()).thenReturn(new ArrayList<>());
        when(applicationController.getUser()).thenReturn(user);

        String viewName = applicationController.applications(model);

        assertEquals("applications", viewName);
        verify(applicationController, times(1)).getUser();
        verify(applicationRepository, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("applications"), any());
    }
    @Test
    void testAssembly() {
        Model model = mock(Model.class);
        Long id = 1L;
        Application application = new Application();
        when(applicationRepository.getReferenceById(id)).thenReturn(application);

        String viewName = applicationController.assembly(model, id);

        assertEquals("applications", viewName);
        verify(applicationRepository, times(1)).save(application);
        verify(model, times(1)).addAttribute(eq("message"), eq("Заявка успешно подтверждена"));
    }

    @Test
    void testPacked() {
        Model model = mock(Model.class);
        Long id = 1L;
        Application application = new Application();
        when(applicationRepository.getReferenceById(id)).thenReturn(application);

        String viewName = applicationController.packed(model, id);

        assertEquals("applications", viewName);
        verify(applicationRepository, times(1)).save(application);
        verify(model, times(1)).addAttribute(eq("message"), eq("Заявка успешно собрана"));
    }

    @Test
    void testDelivery() {
        Model model = mock(Model.class);
        Long id = 1L;
        Application application = new Application();
        when(applicationRepository.getReferenceById(id)).thenReturn(application);

        String viewName = applicationController.delivery(model, id);

        assertEquals("applications", viewName);
        verify(applicationRepository, times(1)).save(application);
        verify(model, times(1)).addAttribute(eq("message"), eq("Заявка успешно упакована"));
    }

    @Test
    void testDelivered() {
        Model model = mock(Model.class);
        Long id = 1L;
        Application application = new Application();
        when(applicationRepository.getReferenceById(id)).thenReturn(application);

        String viewName = applicationController.delivered(model, id);

        assertEquals("applications", viewName);
        verify(applicationRepository, times(1)).save(application);
        verify(model, times(1)).addAttribute(eq("message"), eq("Заявка успешно доставлена"));
    }
}
