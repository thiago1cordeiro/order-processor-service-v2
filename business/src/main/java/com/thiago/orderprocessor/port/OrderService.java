package com.thiago.orderprocessor.port;

import com.thiago.orderprocessor.dto.Order;

public interface OrderService {
    
    Order processOrder(Order order);
    Order findOrderById(Integer orderId);
    void deleteOrderById(Integer orderId);
    
    void deleteItemById(Integer orderId);
    
    void deleteItemById2(Integer orderId);
    
    void deleteItemById3(Integer orderId);
}
