package org.example.ordersservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Orders", description = "API for managing orders")
public class OrderController {
    private final OrderService orderService;

    /**
     * Creates a new order based on the provided order request data.
     *
     * @param request the OrderRequestDto object containing the details of the order to be created.
     * @return a ResponseEntity containing the OrderResponseDto object representing the created order,
     *         with a status of 201 (Created).
     */
    @PostMapping
    @Operation(summary = "Create a new order")
    @ApiResponse(responseCode = "201", description = "Order created")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto request) {
        OrderResponseDto response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id the unique identifier of the order to be retrieved.
     * @return a ResponseEntity containing the OrderResponseDto object representing the order found,
     *         or a 404 status if the order is not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    @ApiResponse(responseCode = "200", description = "Order found")
    @ApiResponse(responseCode = "404", description = "Order not found")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        OrderResponseDto response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a list of orders that match the specified date and amount.
     *
     * @param date the date on which the orders were placed.
     * @param amount the total amount of the orders to be retrieved.
     * @return a ResponseEntity containing a list of OrderResponseDto objects representing the orders found,
     *         or an empty list if no orders match the criteria.
     */
    @GetMapping
    @Operation(summary = "Get orders by date and amount")
    @ApiResponse(responseCode = "200", description = "Orders found")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByDateAndAmount(
            @RequestParam LocalDate date,
            @RequestParam BigDecimal amount) {
        List<OrderResponseDto> response = orderService.getOrdersByDateAndAmount(date, amount);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a list of orders that do not contain a specified product and fall within a given date range.
     *
     * @param productName the name of the product to exclude from the search results.
     * @param startDate the start date of the date range within which to search for orders.
     * @param endDate the end date of the date range within which to search for orders.
     * @return a ResponseEntity containing a list of OrderResponseDto objects representing the orders found,
     *         or an empty list if no orders match the criteria.
     */
    @GetMapping("/filter")
    @Operation(summary = "Get orders without a product and between dates")
    @ApiResponse(responseCode = "200", description = "Orders found")
    public ResponseEntity<List<OrderResponseDto>> getOrdersWithoutProductAndBetweenDates(
            @RequestParam String productName,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<OrderResponseDto> response = orderService.getOrdersWithoutProductAndBetweenDates(productName, startDate, endDate);
        return ResponseEntity.ok(response);
    }
}