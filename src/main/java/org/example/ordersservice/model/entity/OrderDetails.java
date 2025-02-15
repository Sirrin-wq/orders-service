package org.example.ordersservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * This class represents the details of an order.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    /**
     * The unique identifier of the order detail.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The identifier of the article associated with the order detail.
     */
    private Long articleId;

    /**
     * The name of the product associated with the order detail.
     */
    private String productName;

    /**
     * The quantity of the product ordered.
     */
    private Integer quantity;

    /**
     * The unit price of the product.
     */
    private BigDecimal unitPrice;

    /**
     * The order to which this order detail belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
