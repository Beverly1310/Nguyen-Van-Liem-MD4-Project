package com.ra.project.repository;

import com.ra.project.model.cons.OrderStatus;
import com.ra.project.model.entity.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Orders, Long> {
    @Query("select o from Orders o where o.status = :status")
    List<Orders> getOrdersByStatus(OrderStatus status);
}
