package com.ashutosh.hotel.service.hotelservice.repositories;

import com.ashutosh.hotel.service.hotelservice.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {
}
