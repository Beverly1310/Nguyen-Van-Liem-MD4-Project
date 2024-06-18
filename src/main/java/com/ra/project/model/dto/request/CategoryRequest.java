package com.ra.project.model.dto.request;

import com.ra.project.validator.CategoryNameExist;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryRequest {
    @CategoryNameExist
    @NotEmpty(message = "Category name is empty")
    @NotBlank(message = "Category name is blank")
    private String categoryName;
    private String description;
}
