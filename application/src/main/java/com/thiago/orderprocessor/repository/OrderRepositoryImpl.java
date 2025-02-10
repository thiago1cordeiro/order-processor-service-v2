package com.thiago.orderprocessor.repository;

import com.thiago.orderprocessor.dto.Item;
import com.thiago.orderprocessor.dto.Order;
import com.thiago.orderprocessor.entity.ItemEntity;
import com.thiago.orderprocessor.entity.OrderEntity;
import com.thiago.orderprocessor.port.OrderRepository;
import com.thiago.orderprocessor.repository.jpa.ItemRepositoryJpa;
import com.thiago.orderprocessor.repository.jpa.OrderRepositoryJpa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class OrderRepositoryImpl implements OrderRepository {
    
    private final OrderRepositoryJpa orderRepositoryJpa;
    private final ItemRepositoryJpa itemRepositoryJpa;
    
    @Autowired
    public OrderRepositoryImpl(final OrderRepositoryJpa orderRepositoryJpa, final ItemRepositoryJpa itemRepositoryJpa) {
        this.orderRepositoryJpa = orderRepositoryJpa;
        this.itemRepositoryJpa = itemRepositoryJpa;
    }
    
    @Transactional
    @Override
    public Order createOrder(final Order order) {
        final var orderEntity = new OrderEntity()
                .withNumberOrder(order.numberOrder())
                .withCreatedAt(LocalDateTime.now());
        
        final OrderEntity orderSaveInDb = orderRepositoryJpa.save(orderEntity);
        
        final List<ItemEntity> listItemEntity = order.items().stream().map(item -> new ItemEntity()
                .withOrderId(orderSaveInDb)
                .withDistributionCenter(item.distributionCenter())
                .withItemId(item.itemId())
                .withCreatedAt(LocalDateTime.now())
        ).toList();
        
        itemRepositoryJpa.saveAll(listItemEntity);
        
        
        return new Order(
                orderSaveInDb.getId(),
                order.numberOrder(),
                order.items()
        );
    }
    
    @Override
    public Optional<Order> findOrderById(final Integer orderId) {
        return orderRepositoryJpa.findById(orderId).map(orderInDb -> {
                    final var itemInDb = orderInDb.getItems().stream().map(item -> new Item(
                            item.getId(),
                            item.getItemId(),
                            item.getDistributionCenter()
                    )).toList();
                    
                    return Optional.of(new Order(
                            orderInDb.getId(),
                            orderInDb.getNumberOrder(),
                            itemInDb
                    ));
                }
        
        ).orElse(Optional.empty());
        
    }
    
    @Override
    public Boolean checkOrderNumberExistInDb(final String numberOrder) {
        return orderRepositoryJpa.findByNumberOrder(numberOrder).isPresent();
    }
}
