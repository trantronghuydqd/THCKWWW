package com.example.demoSpringCK.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private long price;
    private String category;
    private boolean in_stock;

}
