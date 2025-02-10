package com.thiago.orderprocessor.config;

import com.thiago.orderprocessor.port.DistributionCenterClient;
import com.thiago.orderprocessor.port.OrderRepository;
import com.thiago.orderprocessor.port.OrderService;
import com.thiago.orderprocessor.service.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    
    @Bean
    public OrderService orderService(DistributionCenterClient distributionCenterClient, OrderRepository orderRepository) {
        return new OrderServiceImpl(distributionCenterClient, orderRepository);
    }
}
