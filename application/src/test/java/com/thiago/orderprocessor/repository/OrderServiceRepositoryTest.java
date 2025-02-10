package com.thiago.orderprocessor.repository;

import com.thiago.orderprocessor.dto.Item;
import com.thiago.orderprocessor.dto.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.thiago.orderprocessor.repository")
class OrderServiceRepositoryTest extends BaseTestContainer {
    
    
    @Autowired
    private OrderRepositoryImpl orderRepository;
    
    @BeforeAll
    public void setupOnce() {
        final var item = new Item(
                null,
                123,
                "CD10"
        );
        
        final var order = new Order(
                null,
                "teste_0001",
                List.of(item)
        );
        
        orderRepository.createOrder(order);
        
    }
    
    @Test
    void createOrder() {
        
        final var item = new Item(
                null,
                123,
                "CD10"
        );
        
        final var order = new Order(
                null,
                "teste_0002",
                List.of(item)
        );
        
        final Order orderCreateInDb = orderRepository.createOrder(order);
        
        assertNotNull(orderCreateInDb.id());
        assertEquals(2, orderCreateInDb.id());
        assertEquals("teste_0002", orderCreateInDb.numberOrder());
        
    }
    
    @Test
    void findOrderByIdSuccessTest() {
        
        final Optional<Order> orderInDb = orderRepository.findOrderById(1);
        
        assertNotNull(orderInDb.get().id());
        assertEquals(1, orderInDb.get().id());
        assertEquals("teste_0001", orderInDb.get().numberOrder());
    }
    
    @Test
    void findOrderByIdNotSuccessOrderNotFoundTest() {
        
        final Optional<Order> orderById = orderRepository.findOrderById(-1);
        
        assertTrue(orderById.isEmpty());
    }
    
    
    @Test
    void checkOrderNumberExistInDbSuccessReturnFalseTest() {
        final Boolean orderNumberExistInDb = orderRepository.checkOrderNumberExistInDb("Teste_-1");
        
        assertFalse(orderNumberExistInDb);
    }
    
    @Test
    void checkOrderNumberExistInDbSuccessReturnTrueTest() {
        final Boolean orderNumberExistInDb = orderRepository.checkOrderNumberExistInDb("teste_0001");
        
        assertTrue(orderNumberExistInDb);
    }
}
