package com.anaselwogoud.dressRental.Service.Interface;

import com.anaselwogoud.dressRental.DTO.Response;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DressService {

    Response addNewDress(MultipartFile dressPhoto, String dressSize, BigDecimal dressPrice, String description);

    List<String> getAllDressSizes();

    Response getAllDresses();

    Response deleteDress(Long dressId);

    Response updateDress(Long dressId, String description, String dressSize, BigDecimal dressPrice, MultipartFile photo);

    Response getDressById(Long dressId);

    Response getAvailableDressesByDateAndSize(LocalDate rentalDate, LocalDate returnDate, String dressSize);

    Response getAllAvailableDresses();
}
