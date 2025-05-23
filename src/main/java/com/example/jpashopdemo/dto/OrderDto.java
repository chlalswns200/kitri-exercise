package com.example.jpashopdemo.dto;

import com.example.jpashopdemo.domain.entity.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private int totalPrice;
    private LocalDateTime orderedAt;
    private Long userId;
    private String username;
    private List<OrderItemDto> orderItems = new ArrayList<>();
}
