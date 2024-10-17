package com.anaselwogoud.dressRental.DTO;

import com.anaselwogoud.dressRental.Entity.Bookings;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DressDTO {
    private Long id;
    private String size;
    private BigDecimal price;
    private String dressPhotoUrl;
    private String description;
    private List<BookingDTO> bookings;
}
