package com.tradesuite.repo;

import com.tradesuite.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepo extends JpaRepository<Product, Long> {
    List<Product> findAllByNameContainingAndCategory_Id(String name, Long id);
}
