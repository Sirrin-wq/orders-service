package org.example.ordersservice.repository;

import org.example.ordersservice.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * This interface extends {@link JpaRepository} to provide data access operations for {@link Order} entities.
 * It includes custom query methods for retrieving specific order data based on various criteria.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Finds all orders with the given order date and total amount greater than or equal to the specified amount.
     *
     * @param date    The order date to match.
     * @param amount  The minimum total amount to match.
     * @return A list of orders that match the given criteria.
     */
    List<Order> findByOrderDateAndTotalAmountGreaterThanEqual(LocalDate date, BigDecimal amount);

    /**
     * Finds all orders that do not contain a specific product within the given date range.
     *
     * @param productName  The name of the product to exclude.
     * @param startDate    The start date of the range to match.
     * @param endDate      The end date of the range to match.
     * @return A list of orders that do not contain the specified product within the given date range.
     */
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate AND NOT EXISTS (SELECT i FROM o.items i WHERE i.productName = :productName)")
    List<Order> findOrdersWithoutProductAndBetweenDates(
            @Param("productName") String productName,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
