package com.example.jpashopdemo.domain.repository.repository;

import com.example.jpashopdemo.domain.entity.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
