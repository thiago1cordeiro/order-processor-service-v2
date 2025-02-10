package com.thiago.orderprocessor.port;

import com.thiago.orderprocessor.dto.Order;

import java.util.Optional;

public interface OrderRepository {
    Order createOrder(Order order);
    Optional<Order> findOrderById(Integer orderId);
    Boolean checkOrderNumberExistInDb(String orderNumber);
}
