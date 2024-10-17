package com.anaselwogoud.dressRental.Service.Impl;

import com.anaselwogoud.dressRental.DTO.BookingDTO;
import com.anaselwogoud.dressRental.DTO.Response;
import com.anaselwogoud.dressRental.Entity.Bookings;
import com.anaselwogoud.dressRental.Entity.Dress;
import com.anaselwogoud.dressRental.Entity.Users;
import com.anaselwogoud.dressRental.Exception.GlobalException;
import com.anaselwogoud.dressRental.Repository.BookingRepo;
import com.anaselwogoud.dressRental.Repository.DressRepo;
import com.anaselwogoud.dressRental.Repository.UserRepo;
import com.anaselwogoud.dressRental.Service.Interface.BookingService;
import com.anaselwogoud.dressRental.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepo bookingRepository;
    @Autowired
    private DressServiceImpl dressService;
    @Autowired
    private DressRepo dressRepo;
    @Autowired
    private UserRepo userRepository;

    @Override
    public Response saveBooking(Long dressId, Long userId, Bookings bookingRequest) {
        Response response = new Response();
        try {
            if (bookingRequest.getReturnDate().isBefore(bookingRequest.getRentalDate())) {
                throw new IllegalArgumentException("Check in date must come after check out date");
            }
            Dress dress = dressRepo.findById(dressId).orElseThrow(() -> new GlobalException("Dress Not Found"));
            Users user = userRepository.findById(userId).orElseThrow(() -> new GlobalException("User Not Found"));

            List<Bookings> existingBookings = dress.getBookings();

            if (!dressIsAvailable(bookingRequest, existingBookings)) {
                throw new GlobalException("Dress is not Available for selected date range");
            }

            bookingRequest.setDress(dress);
            bookingRequest.setUser(user);
            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
            bookingRepository.save(bookingRequest);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBookingConfirmationCode(bookingConfirmationCode);

        } catch (GlobalException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Saving a booking: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();
        try {
            Bookings booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(() -> new GlobalException("Booking Not Found"));
            BookingDTO bookingDTO = Utils.mapBookingToBookingDTO(booking);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBookings(bookingDTO);

        } catch (GlobalException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Finding a booking: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();
        try {
            List<Bookings> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingDTOList = Utils.mapBookingListToBookingListDTO(bookingList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBookingList(bookingDTOList);

        } catch (GlobalException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Getting all bookings: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();
        try {
            bookingRepository.findById(bookingId).orElseThrow(() -> new GlobalException("Booking Does Not Exist"));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (GlobalException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Cancelling a booking: " + e.getMessage());
        }
        return response;
    }

    private boolean dressIsAvailable(Bookings bookingRequest, List<Bookings> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getRentalDate().equals(existingBooking.getRentalDate())
                                || bookingRequest.getReturnDate().isBefore(existingBooking.getReturnDate())
                                || (bookingRequest.getRentalDate().isAfter(existingBooking.getRentalDate())
                                && bookingRequest.getRentalDate().isBefore(existingBooking.getReturnDate()))
                                || (bookingRequest.getRentalDate().isBefore(existingBooking.getRentalDate())

                                && bookingRequest.getReturnDate().equals(existingBooking.getReturnDate()))
                                || (bookingRequest.getRentalDate().isBefore(existingBooking.getRentalDate())

                                && bookingRequest.getReturnDate().isAfter(existingBooking.getReturnDate()))

                                || (bookingRequest.getRentalDate().equals(existingBooking.getReturnDate())
                                && bookingRequest.getReturnDate().equals(existingBooking.getRentalDate()))

                                || (bookingRequest.getRentalDate().equals(existingBooking.getReturnDate())
                                && bookingRequest.getReturnDate().equals(bookingRequest.getRentalDate()))
                );
    }
}
