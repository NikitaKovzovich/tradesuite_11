package com.tradesuite.controllers;

import com.tradesuite.model.AppUser;
import com.tradesuite.model.enums.Department;
import com.tradesuite.model.enums.Role;
import com.tradesuite.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testUsers() {
        Model model = mock(Model.class);
        String viewName = userController.users(model);

        assertEquals("users", viewName);
        verify(userRepo, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("users"), any());
        verify(model, times(1)).addAttribute(eq("roles"), eq(Role.values()));
        verify(model, times(1)).addAttribute(eq("departments"), eq(Department.values()));
    }

    @Test
    void testEdit() {
        Long id = 1L;
        Role role = Role.MANAGER;
        Department department = Department.PACKAGING;
        String redirectUrl = "redirect:/users";

        String viewName = userController.edit(id, role, department);

        assertEquals(redirectUrl, viewName);
        verify(userRepo, times(1)).getReferenceById(id);
        verify(userRepo, times(1)).save(any(AppUser.class));
    }

    @Test
    void testDelete() {
        Long id = 1L;
        String redirectUrl = "redirect:/users";

        String viewName = userController.delete(id);

        assertEquals(redirectUrl, viewName);
        verify(userRepo, times(1)).deleteById(id);
    }

    @Test
    void testEdit1() throws Exception {
        Long id = 1L;
        Role role = Role.MANAGER;
        Department department = Department.PACKAGING;

        // Prepare a mock AppUser object
        AppUser user = new AppUser();
        user.setId(id);

        // Mock the behavior of userRepo.save(user)
        when(userRepo.save(user)).thenReturn(user);

        // Perform the POST request
        mockMvc.perform(post("/users/{id}/edit", id)
                        .param("role", role.name())
                        .param("department", department.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        // Verify that setRole and setDepartment methods are called with the correct parameters
        verify(user).setRole(role);
        verify(user).setDepartment(department);

        // Verify that userRepo.save(user) is invoked
        verify(userRepo).save(user);
    }
}
