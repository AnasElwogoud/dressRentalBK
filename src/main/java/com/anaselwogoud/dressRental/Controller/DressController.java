package com.anaselwogoud.dressRental.Controller;

import com.anaselwogoud.dressRental.DTO.Response;
import com.anaselwogoud.dressRental.Service.Interface.BookingService;
import com.anaselwogoud.dressRental.Service.Interface.DressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dress")
public class DressController {

    @Autowired
    private DressService dressService;
    @Autowired
    private BookingService BookingService;


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewDress(
            @RequestParam(value = "dressPhoto", required = false) MultipartFile dressPhoto,
            @RequestParam(value = "dressSize", required = false) String dressSize,
            @RequestParam(value = "dressPrice", required = false) BigDecimal dressPrice,
            @RequestParam(value = "dressDescription", required = false) String dressDescription
    ) {

        if (dressPhoto == null || dressPhoto.isEmpty() || dressSize == null || dressSize.isBlank() || dressPrice == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields (photo, dressSize,dressPrice)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = dressService.addNewDress(dressPhoto, dressSize, dressPrice, dressDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllDresses() {
        Response response = dressService.getAllDresses();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/sizes")
    public List<String> getAllDressSizes() {
        return dressService.getAllDressSizes();
    }

    @GetMapping("/dressById/{dressId}")
    public ResponseEntity<Response> getDressById(@PathVariable Long dressId) {
        Response response = dressService.getDressById(dressId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/allAvailableDresses")
    public ResponseEntity<Response> allAvailableDresses() {
        Response response = dressService.getAllAvailableDresses();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/getAvailableDressByDateAndSize")
    public ResponseEntity<Response> getAvailableDressByDateAndSize(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rentalDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate,
            @RequestParam(required = false) String dressSize
    ) {
        if (rentalDate == null || dressSize == null || dressSize.isBlank() || returnDate == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields (rentalDate,returnDate,dressSize)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = dressService.getAvailableDressesByDateAndSize(rentalDate, returnDate, dressSize);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{dressId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateDress(@PathVariable Long dressId,
                                                @RequestParam(value = "dressPhoto", required = false) MultipartFile dressPhoto,
                                                @RequestParam(value = "dressSize", required = false) String dressSize,
                                                @RequestParam(value = "dressPrice", required = false) BigDecimal dressPrice,
                                                @RequestParam(value = "dressDescription", required = false) String dressDescription

    ) {
        Response response = dressService.updateDress(dressId, dressDescription, dressSize, dressPrice, dressPhoto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{dressId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteDress(@PathVariable Long dressId) {
        Response response = dressService.deleteDress(dressId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
