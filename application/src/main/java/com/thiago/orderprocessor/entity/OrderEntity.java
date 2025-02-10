package com.thiago.orderprocessor.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders", schema = "orders")
public class OrderEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "number_order", nullable = false)
    private String numberOrder;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "update_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemEntity> items;
    
    
}