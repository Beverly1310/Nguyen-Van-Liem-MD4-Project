package com.ra.project.repository;

import com.ra.project.model.cons.OrderStatus;
import com.ra.project.model.entity.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends CrudRepository<Orders, Long> {
    @Query("select o from Orders o where o.status = :status")
    List<Orders> getOrdersByStatus(OrderStatus status);
    List<Orders> getOrdersByUserId(Long userId);
    Orders findOrdersBySerialNumber(String serialNumber);
    List<Orders> getOrdersByStatusAndUserId(OrderStatus status,Long userId);
    @Query("select o from Orders o where o.createdAt between :fromDate and :toDate ")
    List<Orders> getOrdersOverTime(LocalDate fromDate, LocalDate toDate);

}
