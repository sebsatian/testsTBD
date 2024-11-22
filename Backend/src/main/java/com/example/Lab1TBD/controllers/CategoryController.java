package com.example.Lab1TBD.controllers;

import com.example.Lab1TBD.persistence.entities.CategoryEntity;
import com.example.Lab1TBD.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable long id) {
        try {
            CategoryEntity category = categoryService.getCategoryById(id);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 si no se encuentra
            }
            return ResponseEntity.ok(category); // Retorna 200 con la categoría
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 en caso de error
        }
    }

    // Obtener una categoría por nombre
    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryEntity> getCategoryByName(@PathVariable String name) {
        try {
            CategoryEntity category = categoryService.getCategoryByName(name);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 si no se encuentra
            }
            return ResponseEntity.ok(category); // Retorna 200 con la categoría
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 en caso de error
        }
    }

    // Crear una nueva categoría
    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody CategoryEntity category) {
        try {
            if (category.getCategory_name() == null || category.getCategory_name().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre de la categoría no puede estar vacío."); // 400
            }
            categoryService.saveCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body("Categoría creada exitosamente."); // 201
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la categoría."); // 500
        }
    }

    // Actualizar una categoría por ID
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable long id, @RequestBody CategoryEntity category) {
        try {
            CategoryEntity existingCategory = categoryService.getCategoryById(id);
            if (existingCategory == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada."); // 404
            }
            category.setCategory_id(id); // Asegura que el ID sea el correcto
            categoryService.updateCategory(category);
            return ResponseEntity.ok("Categoría actualizada exitosamente."); // 200
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la categoría."); // 500
        }
    }

    // Eliminar una categoría por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        try {
            CategoryEntity existingCategory = categoryService.getCategoryById(id);
            if (existingCategory == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada."); // 404
            }
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Categoría eliminada exitosamente."); // 200
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la categoría."); // 500
        }
    }
}
