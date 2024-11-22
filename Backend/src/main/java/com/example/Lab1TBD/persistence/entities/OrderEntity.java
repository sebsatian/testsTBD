package com.example.Lab1TBD.persistence.entities;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    private Long order_id; // identificador unico de la orden
    private Timestamp date; // fecha en que se realizo la orden
    private String status;    // estado de la orden (pendiente,pagada,enviada)
    private Float total;  // total a pagar de la orden (OJO, todos los productos del cliente)
    private Long client_id; // identificador foraneo del cliente FK
}
