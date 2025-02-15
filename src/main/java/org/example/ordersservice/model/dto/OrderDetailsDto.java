package org.example.ordersservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * This class represents a data transfer object of the details of an order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    private Long articleId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
}
