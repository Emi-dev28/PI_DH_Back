package com.PI_back.pi_back.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "IMAGENES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imagen_url")

    private String imageUrl;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JoinColumn(name = "producto_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Product product;
//    @ManyToOne()
//    @JoinColumn(name = "files")

    @OneToOne(mappedBy = "img", cascade = CascadeType.ALL)
    private Category category;

    public Imagen(String imageUrl, Product product) {
        this.imageUrl = imageUrl;
        this.product = product;
    }


}
