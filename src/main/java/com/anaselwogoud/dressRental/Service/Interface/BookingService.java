package com.anaselwogoud.dressRental.Service.Interface;

import com.anaselwogoud.dressRental.DTO.Response;
import com.anaselwogoud.dressRental.Entity.Bookings;

public interface BookingService {

    Response saveBooking(Long dressId, Long userId, Bookings bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);
}
