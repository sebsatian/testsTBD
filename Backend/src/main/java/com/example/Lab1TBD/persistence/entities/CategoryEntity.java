package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    private long category_id;       // identificador unico de categoria
    private String category_name; // nombre de la categoria
}
