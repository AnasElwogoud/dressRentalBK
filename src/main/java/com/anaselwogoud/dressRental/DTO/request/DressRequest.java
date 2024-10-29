package com.anaselwogoud.dressRental.DTO.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DressRequest {
    private String size;
    private BigDecimal price;
    private byte[] dressPhotoUrl;
    private String description;
}
