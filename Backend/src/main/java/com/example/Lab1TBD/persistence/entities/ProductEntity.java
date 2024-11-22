package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    private long product_id; //identificador unico del producto
    private String product_name;  //nombre del producto
    private String description; //descripcion del producto
    private Float price;   //precio del producto
    private Integer stock;  //cantidad disponible del producto
    private String product_status;  //estado del producto (disponible,agotado)
    private Integer category_id;   //identificador foraneo de categoria FK
}
