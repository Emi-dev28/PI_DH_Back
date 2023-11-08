package com.PI_back.pi_back.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
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
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imagen_url")

    private String imageUrl;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JoinColumn(name = "producto_id")
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @Transient todo>
    private Product product;
//    @ManyToOne()
//    @JoinColumn(name = "files")

    @OneToOne(mappedBy = "img")

    private Category category;

    public Imagen(String imageUrl, Product product) {
        this.imageUrl = imageUrl;
        this.product = product;
    }


}
