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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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


    @Test
    void testCategory() throws Exception {
        List<Category> categories = new ArrayList<>();
        when(categoryRepo.findAll()).thenReturn(categories);

        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("categories", categories))
                .andExpect(view().name("category"));
    }

    @Test
    void testAdd() throws Exception {
        String name = "Test Category";
        mockMvc.perform(post("/category/add").param("name", name))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/category"));

        // Verify that categoryRepo.save was called with the correct parameters
        Category category = new Category(name);
        when(categoryRepo.save(any(Category.class))).thenReturn(category);
    }


    @Test
    void testDelete() throws Exception {
        Long id = 1L;
        doNothing().when(categoryRepo).deleteById(id);

        mockMvc.perform(get("/category/{id}/delete", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/category"));

        // Verify that categoryRepo.deleteById was called with the correct parameter
    }
}
