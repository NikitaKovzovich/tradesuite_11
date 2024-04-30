package com.tradesuite.controllers;

import com.tradesuite.controllers.main.Main;
import com.tradesuite.model.AppUser;
import com.tradesuite.model.Application;
import com.tradesuite.repo.ApplicationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HistoryControllerTest {

    @Mock
    private ApplicationRepo applicationRepo;

    @InjectMocks
    private HistoryController historyController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(historyController).build();
    }

    @Test
    void testHistory() throws Exception {
        List<Application> applications = new ArrayList<>();
        when(applicationRepo.findAll()).thenReturn(applications);

        mockMvc.perform(get("/history").sessionAttr("user", new AppUser()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("applications"))
                .andExpect(view().name("history"));
    }

    @Test
    void testDelete() throws Exception {
        Long id = 1L;
        doNothing().when(applicationRepo).deleteById(id);

        mockMvc.perform(get("/history/{id}/delete", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/history"));
    }
    @Test
    void testHistoryWithApplications() throws Exception {
        // Mocking the Main class
        Main main = Mockito.mock(Main.class);

        // Mocking the AppUser object returned by getUser()
        AppUser appUser = new AppUser();
        when(main.getUser()).thenReturn(appUser);

        // Mocking the list of applications
        List<Application> applications = new ArrayList<>();
        applications.add(new Application());
        when(appUser.getApplications()).thenReturn(applications);

        mockMvc.perform(get("/history").sessionAttr("user", main))
                .andExpect(status().isOk())
                .andExpect(model().attribute("applications", applications))
                .andExpect(view().name("history"));
    }



    @Test
    void testHistoryWithoutApplications() throws Exception {
        when(applicationRepo.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/history").sessionAttr("user", new AppUser()))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("applications"))
                .andExpect(model().attribute("message", "Нет заявок для обработки"))
                .andExpect(view().name("history"));
    }


}
