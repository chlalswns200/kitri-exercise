package com.example.jpashopdemo.repository;

import com.example.jpashopdemo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
