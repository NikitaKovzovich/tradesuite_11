package com.tradesuite.controllers;

import com.tradesuite.controllers.main.Main;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StatsControllerTest {

    @Mock
    private ProductsRepo productsRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private Model model;

    @InjectMocks
    private StatsController statsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStats() {
        // Mock products
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setIncome(1000);
        products.add(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setIncome(2000);
        products.add(product2);

        when(productsRepo.findAll()).thenReturn(products);

        // Mock categories
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setName("Category 1");
        category1.setIncome(500);
        category1.setIncomeQuantity(5);
        categories.add(category1);

        Category category2 = new Category();
        category2.setName("Category 2");
        category2.setIncome(1000);
        category2.setIncomeQuantity(10);
        categories.add(category2);

        when(categoryRepo.findAll()).thenReturn(categories);

        // Invoke the method
        String viewName = statsController.stats(model);

        // Verify that the correct view name is returned
        assertEquals("stats", viewName);

        // Verify that getCurrentUserAndRole method is called
        verify(statsController).getCurrentUserAndRole(model);

        // Verify that products are sorted by income
        Collections.sort(products, Comparator.comparing(Product::getIncome));
        Collections.reverse(products);

        // Verify that the correct attributes are added to the model
        verify(model).addAttribute("products", products);

        // Verify that incomeString and incomeFloat arrays are correctly constructed
        String[] incomeString = {"Product 2", "Product 1", null, null, null};
        float[] incomeFloat = {2000, 1000, 0, 0, 0};
        verify(model).addAttribute("incomeString", incomeString);
        verify(model).addAttribute("incomeFloat", incomeFloat);

        // Verify that categoryString, categoryFloat, and categoryInt arrays are correctly constructed
        String[] categoryString = {"Category 2", "Category 1"};
        float[] categoryFloat = {1000, 500};
        int[] categoryInt = {10, 5};
        verify(model).addAttribute("categoryString", categoryString);
        verify(model).addAttribute("categoryFloat", categoryFloat);
        verify(model).addAttribute("categoryInt", categoryInt);
    }
}
