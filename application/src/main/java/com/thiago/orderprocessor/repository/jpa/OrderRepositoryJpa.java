package com.thiago.orderprocessor.repository.jpa;

import com.thiago.orderprocessor.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepositoryJpa extends JpaRepository<OrderEntity, Integer> {
    
    Optional<OrderEntity> findByNumberOrder(String numberOrder);
    
}
