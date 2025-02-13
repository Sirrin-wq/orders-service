package org.example.ordersservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.ordersservice.model.dto.OrderRequestDto;
import org.example.ordersservice.model.dto.OrderResponseDto;
import org.example.ordersservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto request) {
        OrderResponseDto response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        OrderResponseDto response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrdersByDateAndAmount(
            @RequestParam LocalDate date,
            @RequestParam BigDecimal amount) {
        List<OrderResponseDto> response = orderService.getOrdersByDateAndAmount(date, amount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<OrderResponseDto>> getOrdersWithoutProductAndBetweenDates(
            @RequestParam String productName,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<OrderResponseDto> response = orderService.getOrdersWithoutProductAndBetweenDates(productName, startDate, endDate);
        return ResponseEntity.ok(response);
    }
}