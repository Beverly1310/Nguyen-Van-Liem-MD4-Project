package com.ra.project.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class OrderDetailId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "order_id")
    private Orders order;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "product_id")
    private Product product;
}
