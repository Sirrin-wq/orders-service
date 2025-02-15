package org.example.ordersservice.service;

import org.example.ordersservice.model.dto.OrderRequestDto;
import org.example.ordersservice.model.dto.OrderResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * This interface defines the contract for the Order Service.
 * It provides methods for creating, retrieving, and filtering orders.
 */
public interface OrderService {

    /**
     * Creates a new order based on the provided request.
     *
     * @param request The details of the order to be created.
     * @return The response containing the details of the newly created order.
     */
    OrderResponseDto createOrder(OrderRequestDto request);

    /**
     * Retrieves the order with the specified ID.
     *
     * @param id The unique identifier of the order.
     * @return The response containing the details of the requested order.
     */
    OrderResponseDto getOrderById(Long id);

    /**
     * Retrieves a list of orders that were placed on the specified date and have a total amount greater than or equal to the specified amount.
     *
     * @param date The date of the orders.
     * @param amount The minimum total amount of the orders.
     * @return A list of responses containing the details of the matching orders.
     */
    List<OrderResponseDto> getOrdersByDateAndAmount(LocalDate date, BigDecimal amount);

    /**
     * Retrieves a list of orders that do not contain the specified product and were placed between the specified start and end dates.
     *
     * @param productName The name of the product to exclude from the orders.
     * @param startDate The start date of the orders.
     * @param endDate The end date of the orders.
     * @return A list of responses containing the details of the matching orders.
     */
    List<OrderResponseDto> getOrdersWithoutProductAndBetweenDates(String productName, LocalDate startDate, LocalDate endDate);

}
