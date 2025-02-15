package org.example.ordersservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an order.
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique identifier for the order.
     */
    private String orderNumber;

    /**
     * Total amount of the order.
     */
    private BigDecimal totalAmount;

    /**
     * Date when the order was placed.
     */
    private LocalDate orderDate;

    /**
     * Name of the recipient for the order.
     */
    private String recipient;

    /**
     * Delivery address for the order.
     */
    private String deliveryAddress;

    /**
     * Type of payment for the order.
     */
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    /**
     * Type of delivery for the order.
     */
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    /**
     * List of order details associated with this order.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> items = new ArrayList<>();

    /**
     * Adds an order detail to the list of items for this order.
     *
     * @param item The order detail to be added.
     */
    public void addItem(OrderDetails item) {
        items.add(item);
        item.setOrder(this);
    }
}