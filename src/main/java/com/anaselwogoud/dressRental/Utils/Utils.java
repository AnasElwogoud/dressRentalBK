package com.anaselwogoud.dressRental.Utils;

import com.anaselwogoud.dressRental.DTO.BookingDTO;
import com.anaselwogoud.dressRental.DTO.DressDTO;
import com.anaselwogoud.dressRental.DTO.UserDTO;
import com.anaselwogoud.dressRental.Entity.Bookings;
import com.anaselwogoud.dressRental.Entity.Dress;
import com.anaselwogoud.dressRental.Entity.Users;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static UserDTO mapUserToUserDTO(Users user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static DressDTO mapDressToDressDTO(Dress dress) {
        DressDTO dressDTO = new DressDTO();

        dressDTO.setId(dress.getId());
        dressDTO.setSize(dress.getSize());
        dressDTO.setPrice(dress.getPrice());
        dressDTO.setDressPhotoUrl(dress.getDressPhotoUrl());
        dressDTO.setDescription(dress.getDescription());
        return dressDTO;
    }

    public static BookingDTO mapBookingToBookingDTO(Bookings booking) {
        BookingDTO bookingDTO = new BookingDTO();
        // Map simple fields
        bookingDTO.setId(booking.getId());
        bookingDTO.setRentalDate(booking.getRentalDate());
        bookingDTO.setReturnDate(booking.getReturnDate());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        return bookingDTO;

    }

    public static DressDTO mapDressToDressDTOPlusBookings(Dress dress) {
        DressDTO dressDTO = mapDressToDressDTO(dress);

        if (dress.getBookings() != null) {
            dressDTO.setBookings(dress.getBookings().stream().map(Utils::mapBookingToBookingDTO).collect(Collectors.toList()));
        }
        return dressDTO;
    }

    public static BookingDTO mapBookingToBookingDTOPlusBookedDress(Bookings booking, boolean mapUser) {
        BookingDTO bookingDTO = new BookingDTO();

        // Map simple fields
        bookingDTO.setId(booking.getId());
        bookingDTO.setRentalDate(booking.getRentalDate());
        bookingDTO.setReturnDate(booking.getReturnDate());

        if (mapUser) {
            bookingDTO.setUser(Utils.mapUserToUserDTO(booking.getUser()));
        }
        if (booking.getDress() != null) {
            DressDTO dressDTO = new DressDTO();

            dressDTO.setId(booking.getDress().getId());
            dressDTO.setSize(booking.getDress().getSize());
            dressDTO.setDescription(booking.getDress().getDescription());
            dressDTO.setDressPhotoUrl(booking.getDress().getDressPhotoUrl());
            dressDTO.setPrice(booking.getDress().getPrice());
            bookingDTO.setDress(dressDTO);
        }
        return bookingDTO;
    }

    public static UserDTO mapUserToUserDTOPlusUserBookingsAndDress(Users user) {
        UserDTO userDTO = mapUserToUserDTO(user);

        if (!user.getBookings().isEmpty()) {
            userDTO.setBookings(user.getBookings().stream().map(booking -> mapBookingToBookingDTOPlusBookedDress(booking, true)).collect(Collectors.toList()));
        }
        return userDTO;
    }

    public static List<UserDTO> mapUserListToUserListDTO(List<Users> userList) {
        return userList.stream().map(Utils::mapUserToUserDTO).collect(Collectors.toList());
    }

    public static List<DressDTO> mapDressListToDressListDTO(List<Dress> dressList) {
        return dressList.stream().map(Utils::mapDressToDressDTO).collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingListToBookingListDTO(List<Bookings> bookingList) {
        return bookingList.stream().map(Utils::mapBookingToBookingDTO).collect(Collectors.toList());
    }


}