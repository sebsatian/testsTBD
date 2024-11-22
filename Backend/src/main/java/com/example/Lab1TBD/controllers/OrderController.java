package com.example.Lab1TBD.controllers;

import com.example.Lab1TBD.persistence.entities.OrderEntity;
import com.example.Lab1TBD.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/getall")
    public ResponseEntity<List<OrderEntity>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // consigue una orden a partir de su id asociada
    @GetMapping("/search/id/{id}")
    public ResponseEntity<OrderEntity> getOrdersByIdOrder(@PathVariable Long id){
        OrderEntity foundOrder = orderService.getOrdersByOrderId(id);
        if (foundOrder == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(foundOrder);
    }

    // consigue las ordenes que posean un tipo de estado, independiente del cliente
    @GetMapping("/search/status")
    public ResponseEntity<List<OrderEntity>> getOrdersByStatus(@RequestParam String status){
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    // consigue las ordenes de un cliente a partir del id de ese cliente
    @GetMapping("/search/clientId/{id}")
    public ResponseEntity<List<OrderEntity>> getOrdersByClientId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrdersByClientId(id));
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveOrder(@RequestBody OrderEntity order){
        orderService.saveOrder(order);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateOrder(@RequestBody OrderEntity order){
        orderService.updateOrder(order);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        OrderEntity existingOrder = orderService.getOrdersByOrderId(id);
        if (existingOrder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            orderService.deleteOrderById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
