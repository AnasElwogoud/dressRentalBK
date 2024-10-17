package com.anaselwogoud.dressRental.Repository;

import com.anaselwogoud.dressRental.Entity.Bookings;
import com.anaselwogoud.dressRental.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Bookings, Long> {
//    List<Bookings> findAllById(Long dressId);
//
//    List<Bookings> findByUserId(Long userId);

    Optional<Bookings> findByBookingConfirmationCode(String confirmationCode);
}
