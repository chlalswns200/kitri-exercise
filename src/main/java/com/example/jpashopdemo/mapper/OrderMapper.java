package com.example.jpashopdemo.mapper;

import com.example.jpashopdemo.domain.entity.entity.Order;
import com.example.jpashopdemo.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toOrderDto(Order order);
    Order toEntity(OrderDto orderDto);
}
