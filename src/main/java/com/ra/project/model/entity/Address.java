package com.ra.project.model.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone", length = 15)
    private String phone;
    @Column(name = "recevie_name",length = 50)
    private String recevieName;
    @Column(name = "full_address")
    private String fullAddress;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;
}
