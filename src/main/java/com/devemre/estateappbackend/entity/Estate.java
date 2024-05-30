package com.devemre.estateappbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Entity
@Table(name = "estates")
@Getter
@Setter
public class Estate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "estate_id")
    private int id;

    @Column(name = "address")
    private String address;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private EstateUser estateUser;

    @Column(name = "create_dt")
    private String createDt;

    @Column(name = "is_active")
    private boolean isActive;

}
