package com.PI_back.pi_back;

import com.PI_back.pi_back.dto.ProductDto;
import com.PI_back.pi_back.model.Category;
import com.PI_back.pi_back.model.Characteristic;
import com.PI_back.pi_back.model.Product;
import com.PI_back.pi_back.model.ProductAvailability;
import com.PI_back.pi_back.utils.JsonPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class PiBackApplication {
    public static final Logger Logger = LoggerFactory.getLogger(PiBackApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(PiBackApplication.class, args);
        Logger.info("**********************");
        Logger.info("Server is running...");
        Logger.info("**********************");
        LocalDate localDate = LocalDate.of(2023, 12, 10);
        LocalDate localDateTo = LocalDate.of(2024, 1, 10);
        JsonObject jsonObject = new JsonObject();
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = Product.builder()
                .name("producto")
                .description("una descripcion")
                .price(10.0)
                .categories(
                        Set.of(Category.builder().name("Categoria tal").description("Una descripcion").build()))
                .imagenes(null)
                .stock(10)
                .rating(2.0)
                .characteristics(List.of(Characteristic.builder().description("Una caracteristica").build()))
//                .availability(ProductAvailability.builder().fromDate(localDate).toDate(localDateTo).build())
                .isReserved(false)
                .build();
        ProductDto productDto = ProductDto
                .builder().name("Un proudcto").description("Una descripcion que deberia superar la cantidad de caracteres de la restriccion").price(10.0).categories(Set.of(Category.builder().name("Una categoria").description("Una descripcionasdnasdnja").build()))
                .stock(10).characteristics(List.of(Characteristic.builder().description("categoria asndkask").build())).availability(ProductAvailability.builder().fromDate(localDate).toDate(localDateTo).build()).isReserved(true).build();
        Logger.info("LA ESTRUCTURA DEL PRODUCTO ES {}", JsonPrinter.toString(product));
		Logger.info("LA ESTRUCTURA DEL PRODUCTODTO ES {}", JsonPrinter.toString(productDto));
        //Logger.info("Este es el objecto que se convierte: {}",prod);
        //Logger.info("Con el toString: {}", JsonPrinter.toString(prod));
    }

}
