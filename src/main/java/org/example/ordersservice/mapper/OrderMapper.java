package org.example.ordersservice.mapper;

import org.example.ordersservice.model.dto.OrderDetailsDto;
import org.example.ordersservice.model.dto.OrderResponseDto;
import org.example.ordersservice.model.entity.Order;
import org.example.ordersservice.model.dto.OrderRequestDto;
import org.example.ordersservice.model.entity.OrderDetails;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    /**
     * Maps the provided {@link OrderRequestDto} to an {@link Order} entity.
     *
     * @param request the {@link OrderRequestDto} containing the data to be mapped
     * @return the mapped {@link Order} entity
     */
    Order toEntity(OrderRequestDto request);

    /**
     * Maps the provided {@link Order} entity to a {@link OrderResponseDto}.
     *
     * @param order the {@link Order} entity to be mapped
     * @return the mapped {@link OrderResponseDto}
     */
    @Mapping(target = "items", source = "items")
    OrderResponseDto toResponseDto(Order order);

    /**
     * Maps a list of {@link Order} entities to a list of {@link OrderResponseDto}s.
     *
     * @param orders the list of {@link Order} entities to be mapped
     * @return the mapped list of {@link OrderResponseDto}s
     */
    List<OrderResponseDto> toResponseDtoList(List<Order> orders);

    /**
     * Maps the provided {@link OrderDetailsDto} to an {@link OrderDetails} entity.
     *
     * @param Dto the {@link OrderDetailsDto} containing the data to be mapped
     * @return the mapped {@link OrderDetails} entity
     */
    @Mapping(target = "order", ignore = true)
    OrderDetails toEntity(OrderDetailsDto Dto);

    /**
     * Maps the provided {@link OrderDetails} entity to a {@link OrderDetailsDto}.
     *
     * @param item the {@link OrderDetails} entity to be mapped
     * @return the mapped {@link OrderDetailsDto}
     */
    OrderDetailsDto toItemDto(OrderDetails item);
}
