package com.example.jpashopdemo.domain.repository.repository;

import com.example.jpashopdemo.domain.entity.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
