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
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
}
