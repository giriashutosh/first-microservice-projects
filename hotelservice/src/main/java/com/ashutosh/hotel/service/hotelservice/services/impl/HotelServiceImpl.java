package com.ashutosh.hotel.service.hotelservice.services.impl;

import com.ashutosh.hotel.service.hotelservice.entities.Hotel;
import com.ashutosh.hotel.service.hotelservice.exceptions.ResourceNotFoundException;
import com.ashutosh.hotel.service.hotelservice.repositories.HotelRepository;
import com.ashutosh.hotel.service.hotelservice.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public Hotel createHotel(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel getHotelById(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with the given id" + id));
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }
}
