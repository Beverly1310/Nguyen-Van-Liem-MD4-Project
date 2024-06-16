package com.ra.project.model.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryRequest {
    private String categoryName;
    private String description;
}
