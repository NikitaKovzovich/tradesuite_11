package com.tradesuite.controllers;

import com.tradesuite.model.Category;
import com.tradesuite.model.Product;
import com.tradesuite.repo.CategoryRepo;
import com.tradesuite.repo.ProductsRepo;
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

class StatsControllerTest {

    @Mock
    private ProductsRepo productRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private StatsController statsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStats() {
        Model model = mock(Model.class);
        List<Product> products = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        when(productRepo.findAll()).thenReturn(products);
        when(categoryRepo.findAll()).thenReturn(categories);

        String viewName = statsController.stats(model);

        assertEquals("stats", viewName);
        verify(model, times(1)).addAttribute(eq("products"), eq(products));
        verify(model, times(1)).addAttribute(eq("incomeString"), any());
        verify(model, times(1)).addAttribute(eq("incomeFloat"), any());
        verify(model, times(1)).addAttribute(eq("categoryString"), any());
        verify(model, times(1)).addAttribute(eq("categoryFloat"), any());
        verify(model, times(1)).addAttribute(eq("categoryInt"), any());
    }
}
