package com.thiago.orderprocessor.service;

import com.thiago.orderprocessor.dto.Item;
import com.thiago.orderprocessor.dto.Order;
import com.thiago.orderprocessor.exception.BadRequestException;
import com.thiago.orderprocessor.exception.CustomsNotFoundException;
import com.thiago.orderprocessor.exception.OrderExistInDbException;
import com.thiago.orderprocessor.port.DistributionCenterClient;
import com.thiago.orderprocessor.port.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.thiago.orderprocessor.mock.OrderServiceMock.mockDistributionCenters;
import static com.thiago.orderprocessor.mock.OrderServiceMock.mockOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    
    @Mock
    private OrderRepository orderRepository;
    
    @Mock
    private DistributionCenterClient distributionCenterClient;
    
    @InjectMocks
    private OrderServiceImpl orderService;
    
    @Test
    void processOrderSuccessTest() {
        
        when(orderRepository.checkOrderNumberExistInDb(any())).thenReturn(false);
        when(distributionCenterClient.getDistributionCenterByItemId(any())).thenReturn(mockDistributionCenters());
        when(orderRepository.createOrder(any())).thenReturn(mockOrder());
        
        final Order order = orderService.processOrder(mockOrder());
        
        assertNotNull(order);
        assertNotNull(order.id());
        
        verify(orderRepository, times(1)).checkOrderNumberExistInDb(any());
        verify(orderRepository, times(1)).createOrder(any());
        verify(distributionCenterClient, times(1)).getDistributionCenterByItemId(any());
    }
    
    @Test
    void processOrderNotSuccessCenterNotFoundTest() {
        
        final Order newOrder = new Order(
                mockOrder().id(),
                mockOrder().numberOrder(),
                mockOrder().items()
        );
        
        when(orderRepository.checkOrderNumberExistInDb(any())).thenReturn(false);
        when(distributionCenterClient.getDistributionCenterByItemId(any())).thenReturn(null);
        
        final var error = assertThrows(CustomsNotFoundException.class, () ->
                orderService.processOrder(newOrder));
        
        assertEquals("No distribution center found for this item 123", error.getMessage());
        
        verify(orderRepository, times(1)).checkOrderNumberExistInDb(any());
        verify(orderRepository, times(0)).createOrder(any());
        verify(distributionCenterClient, times(1)).getDistributionCenterByItemId(any());
        
    }
    
    @Test
    void processOrderNotSuccessNumberOderExistInDbTest() {
        
        when(orderRepository.checkOrderNumberExistInDb(any())).thenReturn(true);
        
        final var error = assertThrows(OrderExistInDbException.class, () -> orderService.processOrder(mockOrder()));
        
        assertEquals("Order exist in db Teste_0001", error.getMessage());
        
        verify(orderRepository, times(1)).checkOrderNumberExistInDb(any());
        verify(orderRepository, times(0)).createOrder(any());
        verify(distributionCenterClient, times(0)).getDistributionCenterByItemId(any());
    }
    
    @Test
    void processOrderNotSuccessOrderWithMoreThanOneHundredItemsTest() {
        
        final var item = new Item(
                null,
                123,
                "CD10"
        );
        
        final List<Item> itemList = new ArrayList<>();
        
        for (int i = 0; i <= 100; i++) {
            itemList.add(item);
        }
        
        final var newOrder = new Order(
                mockOrder().id(),
                mockOrder().numberOrder(),
                itemList
        );
        
        final var error = assertThrows(BadRequestException.class, () ->
                orderService.processOrder(newOrder));
        
        assertEquals("Items cannot be larger than 100!", error.getMessage());
        
        verify(orderRepository, times(0)).checkOrderNumberExistInDb(any());
        verify(orderRepository, times(0)).createOrder(any());
        verify(distributionCenterClient, times(0)).getDistributionCenterByItemId(any());
    }
    
    @Test
    void findOrderByIdSuccessTest() {
        
        when(orderRepository.findOrderById(1)).thenReturn(Optional.of(mockOrder()));
        
        orderService.findOrderById(1);
        
        verify(orderRepository, times(1)).findOrderById(1);
    }
    
    @Test
    void findOrderByIdNotSuccessOrderNotFoundTest() {
        var orderId = -1;
        when(orderRepository.findOrderById(orderId)).thenReturn(Optional.empty());
        
        final CustomsNotFoundException error = assertThrows(CustomsNotFoundException.class, () -> orderService.findOrderById(orderId));
        
        assertEquals("Order not found by id -1", error.getMessage());
        
        verify(orderRepository, times(1)).findOrderById(orderId);
    }
}
