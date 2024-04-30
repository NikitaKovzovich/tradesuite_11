package com.tradesuite.controllers;

import com.tradesuite.model.Application;
import com.tradesuite.model.Category;
import com.tradesuite.model.Product;
import com.tradesuite.repo.ApplicationRepo;
import com.tradesuite.repo.CategoryRepo;
import com.tradesuite.repo.ProductsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductsRepo productsRepo;

    @Mock
    private ApplicationRepo applicationRepo;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApp() {
        Model model = mock(Model.class);
        Long productId = 1L;
        int quantity = 5;
        String contact = "test_contact";
        String address = "test_address";
        Product product = new Product();
        when(productsRepo.getReferenceById(productId)).thenReturn(product);

        String viewName = productController.app(model, productId, quantity, contact, address);

        assertEquals("product", viewName);
        verify(applicationRepo, times(1)).save(any(Application.class));
        verify(model, times(1)).addAttribute(eq("message"), eq("Заявка успешно подана"));
        verify(model, times(1)).addAttribute(eq("product"), eq(product));
    }


    @Mock
    private CategoryRepo categoryRepo;


    @Mock
    private Model model;



    @Test
    void testEdit() throws IOException {
        Long id = 1L;
        String name = "Test Product";
        String date = "2024-04-30";
        int term = 12;
        String origin = "Test Origin";
        String firm = "Test Firm";
        float price = 100.0f;
        String description = "Test Description";
        Long categoryId = 1L;
        MockMultipartFile photo = new MockMultipartFile("photo", "test.jpg", "image/jpeg", "Some Image".getBytes());

        Product existingProduct = new Product();
        existingProduct.setId(id);

        when(productsRepo.getReferenceById(id)).thenReturn(existingProduct);
        when(categoryRepo.getReferenceById(categoryId)).thenReturn(new Category());

        String expectedRedirectUrl = "redirect:/products/" + id;

        String result = productController.edit(
                model, name, date, term, origin, firm, price, description, categoryId, photo, id
        );

        assertEquals(expectedRedirectUrl, result);

        // Verify that the product is correctly updated
        verify(existingProduct).set(name, date, term, origin, firm, price, description, any(Category.class));

        // Verify that the product is saved
        verify(productsRepo).save(existingProduct);
    }
}
