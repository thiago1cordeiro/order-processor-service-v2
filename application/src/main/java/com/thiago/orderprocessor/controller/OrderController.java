package com.thiago.orderprocessor.controller;

import com.thiago.orderprocessor.controller.documentation.OrderControllerDocumentation;
import com.thiago.orderprocessor.dto.Order;
import com.thiago.orderprocessor.port.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController implements OrderControllerDocumentation {
    
    
    private final OrderService orderService;
    
    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }
    
    @Override
    @PostMapping
    public ResponseEntity<Order> orderProcessor(@RequestBody final Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.processOrder(order));
    }
    
    @Override
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") final Integer orderId) {
        return ResponseEntity.ok().body(orderService.findOrderById(orderId));
    }
    
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrderById(@PathVariable("orderId") final Integer orderId){
        orderService.deleteOrderById(orderId);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/item/{orderId}")
    public ResponseEntity<?> deleteItemById(@PathVariable("itemId") final Integer orderId){
        orderService.deleteItemById(orderId);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/item/{orderId}")
    public ResponseEntity<?> deleteItemById2(@PathVariable("itemId") final Integer orderId){
        orderService.deleteItemById2(orderId);
        return ResponseEntity.noContent().build();
    }
}
