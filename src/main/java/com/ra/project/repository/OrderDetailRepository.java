package com.ra.project.repository;

import com.ra.project.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("select od from OrderDetail od where od.id.order.orderId=:orderId")
    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
}
