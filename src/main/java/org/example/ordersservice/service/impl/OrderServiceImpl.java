package org.example.ordersservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ordersservice.mapper.OrderMapper;
import org.example.ordersservice.model.dto.OrderRequestDto;
import org.example.ordersservice.model.dto.OrderResponseDto;
import org.example.ordersservice.model.entity.Order;
import org.example.ordersservice.model.entity.OrderDetails;
import org.example.ordersservice.repository.OrderRepository;
import org.example.ordersservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * This class implements the OrderService interface and provides methods for managing orders.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestTemplate restTemplate;

    private static final String NUMBER_GENERATE_SERVICE_URL = "http://number-generate-service:80/numbers";

    /**
     * Creates a new order based on the provided request data.
     *
     * @param request The order request data.
     * @return The created order response data.
     */
    @Override
    public OrderResponseDto createOrder(OrderRequestDto request) {
        String orderNumber = getOrderNumberFromNumberGenerateService();

        Order order = orderMapper.toEntity(request);
        order.setOrderNumber(orderNumber);
        order.setOrderDate(LocalDate.now());

        List<OrderDetails> items = request.getItems().stream()
                .map(dto -> {
                    OrderDetails item = orderMapper.toEntity(dto);
                    item.setOrder(order);
                    return item;
                })
                .toList();

        order.setItems(items);

        BigDecimal totalAmount = order.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponseDto(savedOrder);
    }

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id The unique identifier of the order.
     * @return The order response data.
     * @throws RuntimeException If the order is not found.
     */
    @Override
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toResponseDto(order);
    }

    /**
     * Retrieves a list of orders based on the specified date and total amount.
     *
     * @param date   The order date.
     * @param amount The minimum total amount of the orders.
     * @return The list of order response data.
     */
    @Override
    public List<OrderResponseDto> getOrdersByDateAndAmount(LocalDate date, BigDecimal amount) {
        List<Order> orders = orderRepository.findByOrderDateAndTotalAmountGreaterThanEqual(date, amount);
        return orderMapper.toResponseDtoList(orders);
    }

    /**
     * Retrieves a list of orders that do not contain a specific product and were placed between the specified dates.
     *
     * @param productName The name of the product to exclude.
     * @param startDate   The start date of the order placement.
     * @param endDate     The end date of the order placement.
     * @return The list of order response data.
     */
    @Override
    public List<OrderResponseDto> getOrdersWithoutProductAndBetweenDates(String productName, LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderRepository.findOrdersWithoutProductAndBetweenDates(productName, startDate, endDate);
        return orderMapper.toResponseDtoList(orders);
    }

    /**
     * Retrieves an order number from the number-generate-service.
     *
     * @return The order number.
     * @throws RuntimeException If the order number cannot be retrieved.
     */
    private String getOrderNumberFromNumberGenerateService() {
        ResponseEntity<String> response = restTemplate.getForEntity(NUMBER_GENERATE_SERVICE_URL, String.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to get order number from number-generate-service");
        }
    }
}