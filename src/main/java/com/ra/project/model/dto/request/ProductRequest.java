package com.ra.project.model.dto.request;

import com.ra.project.model.entity.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequest {
    private Long id;
    @Column(name = "product_name",length = 100)
    @NotBlank
    @NotEmpty
    private String productName;
    @Column(name = "description")
    @Lob
    private String description;
    @Column(name = "unit_price", columnDefinition = "DECIMAL(10,2)")
    private Double unitPrice;
    @Column(name = "stock_quantity")
    @Min(1)
    private Long stockQuantity;
    @Column(name = "image")
    private String image;
    private Long categoryId;
}
