package com.ra.project.model.dto.request;

import com.ra.project.model.entity.Category;
import com.ra.project.validator.ProductNameExist;
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
    @NotBlank(message = "Product name is blank")
    @NotEmpty(message = "Product name is empty")
    @ProductNameExist
    private String productName;
    private String description;
    private Double unitPrice;
    @Min(value = 1,message = "Stock must greater than 1")
    private Long stockQuantity;
    private String image="https://gebelesebeti.ge/front/asset/img/default-product.png";
    private Long categoryId;
}
