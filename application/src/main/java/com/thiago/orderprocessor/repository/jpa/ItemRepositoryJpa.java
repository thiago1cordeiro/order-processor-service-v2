package com.thiago.orderprocessor.repository.jpa;

import com.thiago.orderprocessor.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepositoryJpa extends JpaRepository<ItemEntity, Integer> {
    void deleteByOrderId(Integer orderId);
}
