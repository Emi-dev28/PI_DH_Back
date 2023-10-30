package com.PI_back.pi_back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SUB-CATEGORIA")
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory extends Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NOMBRE")
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
