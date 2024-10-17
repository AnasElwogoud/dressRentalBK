package com.anaselwogoud.dressRental.Repository;

import com.anaselwogoud.dressRental.Entity.Bookings;
import com.anaselwogoud.dressRental.Entity.Dress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DressRepo extends JpaRepository<Dress, Long> {

    @Query("SELECT DISTINCT d.size FROM Dress d")
    List<String> findDistinctSize();


    @Query("SELECT d FROM Dress d WHERE d.size = :size AND d.id NOT IN (SELECT bk.id FROM Bookings bk WHERE" +
            "(bk.rentalDate <= :rentalDate) AND (bk.returnDate >= :returnDate))")
    List<Dress> findAvailableDressByDatesAndSize(LocalDate rentalDate, LocalDate returnDate, String size);


    @Query("SELECT d FROM Dress d WHERE d.id NOT IN (SELECT b.dress.id FROM Bookings b)")
    List<Dress> getAllAvailableDress();
}
