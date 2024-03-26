package com.ashutosh.hotel.service.hotelservice.services;

import com.ashutosh.hotel.service.hotelservice.entities.Hotel;

import java.util.List;

public interface HotelService {
    //create hotel
    Hotel createHotel(Hotel hotel);
    //get hotel by id
    Hotel getHotelById(String id);
    //get all hotels
    List<Hotel> getAllHotels();
}
