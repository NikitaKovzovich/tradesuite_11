package com.tradesuite.controllers;

import com.tradesuite.model.Application;
import com.tradesuite.model.Product;
import com.tradesuite.repo.ApplicationRepo;
import com.tradesuite.repo.ProductsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

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
}
