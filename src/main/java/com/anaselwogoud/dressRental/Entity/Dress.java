package com.anaselwogoud.dressRental.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
public class Dress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;
    private BigDecimal price;
    @Lob
    @Column(length = 1000000)
    private byte[] dressPhotoUrl;

//    private String dressPhotoUrl;
    private String description;
    @OneToMany(mappedBy = "dress", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bookings> bookings = new ArrayList<>();
}
