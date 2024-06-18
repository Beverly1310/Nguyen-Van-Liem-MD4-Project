package com.ra.project.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ra.project.model.cons.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "serial_number", length = 100, unique = true)
    private String serialNumber= UUID.randomUUID().toString();
    @Column(name = "total_price", columnDefinition = "DECIMAL(10,2)")
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    @Column(name = "note", length = 100)
    private String note;
    @Column(name = "receive_name", length = 100)
    private String receiveName;
    @Column(name = "receive_address")
    private String receiveAddress;
    @Column(name = "receive_phone", length = 15)
    private String receivePhone;
    @Column(name = "created_at", columnDefinition = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM-đ")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt=LocalDate.now();
    @Column(name = "received_at", columnDefinition = "DATE")
    private LocalDate receivedAt=createdAt.plusDays(4);
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
