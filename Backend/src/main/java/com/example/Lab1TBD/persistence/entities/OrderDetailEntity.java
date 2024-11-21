package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailEntity {
    private Long order_detail_id; //identificador unico del detalle
    private int quantity; // cantidad del producto en la orden
    private Float price; // precio unitario del producto
    private Long order_id; // id foraneo que pertenece a la orden FK
    private Long product_id; // id foraneo que pertenece al producto FK
}

