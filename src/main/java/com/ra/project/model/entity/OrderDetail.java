package com.ra.project.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order_detail")
public class OrderDetail {
   @EmbeddedId
    private OrderDetailId id;
   @Column(name = "name",length = 100)
    private String name;
    @Column(name = "unit_price", columnDefinition = "DECIMAL(10,2)")

    private Double unitPrice;
    @Column(name = "order_quantity")
    @Min(0)
    private Long orderQuantity;
}
