package com.thiago.orderprocessor.mock;

import com.thiago.orderprocessor.dto.DistributionCenter;
import com.thiago.orderprocessor.dto.Item;
import com.thiago.orderprocessor.dto.Order;

import java.util.List;

public class OrderServiceMock {
    
    public static DistributionCenter mockDistributionCenters() {
        return new DistributionCenter(List.of("CD1", "CD2"));
    }
    
    public static Order mockOrder() {
        return new Order(
                1,
                "Teste_0001",
                List.of(mockItem())
        );
    }
    
    public static Item mockItem() {
        return new Item(
                null,
                123,
                "CD10"
        );
    }
}