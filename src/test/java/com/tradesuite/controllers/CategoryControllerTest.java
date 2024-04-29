package com.tradesuite.controllers;

import com.tradesuite.model.Category;
import com.tradesuite.repo.CategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

class CategoryControllerTest {

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void testEdit() throws Exception {
        Long id = 1L;
        String name = "Updated Category Name";

        // Mocking category retrieval
        Category category = new Category("Initial Category Name");
        when(categoryRepo.getReferenceById(id)).thenReturn(category);

        mockMvc.perform(post("/category/{id}/edit", id)
                        .param("name", name))
                .andExpect(redirectedUrl("/category"));

        // Verifying that the repository save method is called with the updated category
        verify(categoryRepo, times(1)).save(category);
        // Verifying that the set method is called with the updated name
        verify(category, times(1)).set(name);
    }
}
