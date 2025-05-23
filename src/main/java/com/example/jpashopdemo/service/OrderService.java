package com.example.jpashopdemo.service;

import com.example.jpashopdemo.domain.entity.entity.Order;
import com.example.jpashopdemo.domain.entity.entity.OrderItem;
import com.example.jpashopdemo.domain.entity.entity.Product;
import com.example.jpashopdemo.domain.entity.entity.User;
import com.example.jpashopdemo.domain.repository.repository.OrderItemRepository;
import com.example.jpashopdemo.domain.repository.repository.OrderRepository;
import com.example.jpashopdemo.domain.repository.repository.ProductRepository;
import com.example.jpashopdemo.domain.repository.repository.UserRepository;
import com.example.jpashopdemo.dto.CreateOrderRequest;
import com.example.jpashopdemo.dto.OrderDto;
import com.example.jpashopdemo.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    @Transactional
    public OrderDto createOrder(CreateOrderRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found with id: " + request.getUserId()));

        Order order = Order.builder()
                .totalPrice(0)
                .orderedAt(LocalDateTime.now())
                .user(user)
                .orderItems(new ArrayList<>())
                .build();

        Order savedOrder = orderRepository.save(order);

        int totalPrice = 0;
        List<CreateOrderRequest.OrderItemRequest> orderItems = request.getOrderItems();
        for (CreateOrderRequest.OrderItemRequest orderItem : orderItems) {
            Product product = productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Product not found with id: " + orderItem.getProductId()));
            if (product.getStock() < orderItem.getQuantity()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Product stock less than quantity");
            }
            int itemPrice = product.getPrice() * orderItem.getQuantity();
            totalPrice += itemPrice;

            OrderItem orderItemEntity = OrderItem.builder()
                    .quantity(orderItem.getQuantity())
                    .itemPrice(itemPrice)
                    .order(order)
                    .product(product)
                    .build();

            orderItemRepository.save(orderItemEntity);
            savedOrder.getOrderItems().add(orderItemEntity);

            productService.updateProductStock(product.getId(), product.getStock() - orderItem.getQuantity());
        }
        savedOrder.setTotalPrice(totalPrice);
        Order updatedOrder = orderRepository.save(savedOrder);
        return orderMapper.toOrderDto(updatedOrder);
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order not found with id: " + id));
        return orderMapper.toOrderDto(order);
    }

    public List<OrderDto> getOrdersByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }

        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order not found with id: " + id));

        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            productService.updateProductStock(product.getId(), product.getStock() + item.getQuantity());
        }

        orderRepository.deleteById(id);
    }
}
