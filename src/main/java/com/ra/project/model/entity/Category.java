package com.ra.project.model.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    @Column(name = "category_name",length = 100)
    private String categoryName;
    @Column(name = "description")
    @Lob
    private String description;
    @Column(name = "status")
    private Boolean status=true;
}
