package com.ra.project.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(name = "sku",length = 100)
    private String sku= UUID.randomUUID().toString();
    @Column(name = "product_name",length = 100)
    private String productName;
    @Column(name = "description")
    private String description;
    @Column(name = "unit_price", columnDefinition = "DECIMAL(10,2)")
    private Double unitPrice;
    @Column(name = "stock_quantity")
    @Min(1)
    private Long stockQuantity;
    @Column(name = "image")
    private String image;
    @Column(name = "created_at", columnDefinition = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM-đ")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt=LocalDate.now();
    @Column(name = "updated_at", columnDefinition = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM-đ")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate updatedAt=LocalDate.now();
    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    private Category category;
}
