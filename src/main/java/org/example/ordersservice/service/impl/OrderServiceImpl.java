package org.example.ordersservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ordersservice.mapper.OrderMapper;
import org.example.ordersservice.model.dto.OrderRequestDto;
import org.example.ordersservice.model.dto.OrderResponseDto;
import org.example.ordersservice.model.entity.Order;
import org.example.ordersservice.repository.OrderRepository;
import org.example.ordersservice.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto request) {
        Order order = orderMapper.toEntity(request);
        order.setOrderDate(LocalDate.now());

        //TODO: add request for orderNumber field

        BigDecimal totalAmount = order.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponseDto(savedOrder);
    }

    // TODO: add custom exceptions
    @Override
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toResponseDto(order);
    }

    @Override
    public List<OrderResponseDto> getOrdersByDateAndAmount(LocalDate date, BigDecimal amount) {
        List<Order> orders = orderRepository.findByOrderDateAndTotalAmountGreaterThanEqual(date, amount);
        return orderMapper.toResponseDtoList(orders);
    }

    @Override
    public List<OrderResponseDto> getOrdersWithoutProductAndBetweenDates(String productName, LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderRepository.findOrdersWithoutProductAndBetweenDates(productName, startDate, endDate);
        return orderMapper.toResponseDtoList(orders);
    }
}