package com.thiago.orderprocessor.service;

import com.thiago.orderprocessor.dto.DistributionCenter;
import com.thiago.orderprocessor.dto.Item;
import com.thiago.orderprocessor.dto.Order;
import com.thiago.orderprocessor.exception.BadRequestException;
import com.thiago.orderprocessor.exception.CustomsNotFoundException;
import com.thiago.orderprocessor.exception.InternalServerErrorException;
import com.thiago.orderprocessor.exception.OrderExistInDbException;
import com.thiago.orderprocessor.port.DistributionCenterClient;
import com.thiago.orderprocessor.port.OrderRepository;
import com.thiago.orderprocessor.port.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OrderServiceImpl implements OrderService {
    
    private final DistributionCenterClient distributionCenterClient;
    private final OrderRepository orderRepository;
    
    public OrderServiceImpl(DistributionCenterClient distributionCenterClient, final OrderRepository orderRepository) {
        this.distributionCenterClient = distributionCenterClient;
        this.orderRepository = orderRepository;
    }
    
    @Override
    public Order processOrder(final Order order) {
        validationDatas(order);
        
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            
            final List<Future<Item>> futures = addCenterDistributionInItemOrder(order, executor);
            
            final List<Item> itemsWithDistributionCenter = getMapperFutureByItem(futures);
            
            final var newOrderByCreate = mapperNewOrder(order, itemsWithDistributionCenter);
            
            return orderRepository.createOrder(newOrderByCreate);
        }
    }
    
    private List<Future<Item>> addCenterDistributionInItemOrder(final Order order, final ExecutorService executor) {
        return order.items().stream()
                .map(item -> executor.submit(() -> processItemOrder(item)))
                .toList();
    }
    
    private static Order mapperNewOrder(final Order order, final List<Item> itemsWithDistributionCenter) {
        return new Order(
                null,
                order.numberOrder(),
                itemsWithDistributionCenter
        );
    }
    
    private static List<Item> getMapperFutureByItem(final List<Future<Item>> futures) {
        return futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw Optional.ofNullable(e.getCause())
                                .filter(CustomsNotFoundException.class::isInstance)
                                .map(CustomsNotFoundException.class::cast)
                                .orElseThrow(() -> new InternalServerErrorException(STR."Error in process future item \{e.getMessage()}"));
                    }
                })
                .toList();
    }
    
    private Item processItemOrder(Item item) {
        final DistributionCenter distributionCenterByItemId = distributionCenterClient.getDistributionCenterByItemId(item.itemId());
        
        final String distributionCenter = Optional.ofNullable(distributionCenterByItemId)
                .map(DistributionCenter::distribuitionCenters)
                .filter(list -> !list.isEmpty())
                .flatMap(list -> list.stream().findFirst())
                .orElseThrow(() -> new CustomsNotFoundException(
                        STR."No distribution center found for this item \{item.itemId()}"
                ));
        
        return new Item(
                null,
                item.itemId(),
                distributionCenter
        );
    }
    
    private void validationDatas(final Order order) {
        if (order.items().size() > 100)
            throw new BadRequestException("Items cannot be larger than 100!");
        
        final Boolean orderNumberExistInDb = orderRepository.checkOrderNumberExistInDb(order.numberOrder());
        if (orderNumberExistInDb)
            throw new OrderExistInDbException(STR."Order exist in db \{order.numberOrder()}");
    }
    
    @Override
    public Order findOrderById(final Integer orderId) {
        return orderRepository.findOrderById(orderId).orElseThrow(
                () -> new CustomsNotFoundException(STR."Order not found by id \{orderId}")
        );
    }
    
    @Override
    public void deleteOrderById(final Integer orderId) {
        orderRepository.deleteOrderById(orderId);
    }
    
    @Override
    public void deleteItemById(final Integer orderId) {
        if (orderId == 1) {
            System.out.println("Item ok");
            return;
        }
        
        throw new BadRequestException("Error");
    }
    
    @Override
    public void deleteItemById2(final Integer orderId) {
        if (orderId == 1) {
            System.out.println("Item ok");
            return;
        }
        
        throw new BadRequestException("Error");
    }
    
    
    @Override
    public void deleteItemById3(final Integer orderId) {
        if (orderId == 1) {
            System.out.println("Item ok");
            return;
        }
        
        throw new BadRequestException("Error");
    }
    
    
}
