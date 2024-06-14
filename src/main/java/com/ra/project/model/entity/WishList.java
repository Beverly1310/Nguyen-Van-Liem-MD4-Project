package com.ra.project.model.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "wish_list")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_list_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_Id",referencedColumnName = "product_id")
    private Product product;

}
