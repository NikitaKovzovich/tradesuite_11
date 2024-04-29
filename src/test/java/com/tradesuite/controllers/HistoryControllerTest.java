package com.tradesuite.controllers;

import com.tradesuite.model.AppUser;
import com.tradesuite.model.Application;
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

class HistoryControllerTest {

    @Mock
    private ApplicationRepo applicationRepo;

    @InjectMocks
    private HistoryController historyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHistory() {
        Model model = mock(Model.class);
        AppUser user = new AppUser();
        List<Application> applications = new ArrayList<>();
        user.setApplications(applications);
        when(historyController.getUser()).thenReturn(user);

        String viewName = historyController.history(model);

        assertEquals("history", viewName);
        verify(historyController, times(1)).getUser();
        verify(model, times(1)).addAttribute(eq("applications"), eq(applications));
    }

    @Test
    void testDelete() {
        Long applicationId = 1L;

        String viewName = historyController.delete(applicationId);

        assertEquals("redirect:/history", viewName);
        verify(applicationRepo, times(1)).deleteById(applicationId);
    }
}
