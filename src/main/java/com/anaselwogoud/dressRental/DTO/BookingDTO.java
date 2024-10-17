package com.anaselwogoud.dressRental.DTO;

import com.anaselwogoud.dressRental.Entity.Bookings;
import com.anaselwogoud.dressRental.Entity.Dress;
import com.anaselwogoud.dressRental.Entity.Users;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    private Long id;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private String bookingConfirmationCode;

    private UserDTO user;
    private DressDTO dress;

}
