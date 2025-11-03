package com.example.demoSpringCK.controller;

import com.example.demoSpringCK.entity.Product;
import com.example.demoSpringCK.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String listProduct(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "/products/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String showNewForm(Model model){
        model.addAttribute("product", new Product());
        return "/products/form";
    }
    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createProduct(@ModelAttribute Product product){
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyRoles('ADMIN','STAFF')")
    public String showEditForm(@PathVariable int id, Model model){
        model.addAttribute("product", productRepository.findById(id).orElseThrow());
        return "/products/form";
    }
    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyRoles('ADMIN','STAFF')")
    public String updateProduct(@PathVariable int id, @ModelAttribute Product product){
        product.setId(id);
        productRepository.save(product);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProduct(@PathVariable int id){
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}
