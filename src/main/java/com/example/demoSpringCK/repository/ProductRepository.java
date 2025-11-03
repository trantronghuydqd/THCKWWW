package com.example.demoSpringCK.repository;

import com.example.demoSpringCK.entity.Product;
import org.hibernate.dialect.function.ListaggStringAggEmulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    public List<Product> findByNameContainingIgnoreCase(String keyword);
}
