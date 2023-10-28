package com.PI_back.pi_back.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SUB-CATEGORIA")
public class SubCategory extends Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NOMBRE")
    private String name;

}
