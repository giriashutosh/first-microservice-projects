package com.ashutosh.ratingservice.ratingservice.services;

import com.ashutosh.ratingservice.ratingservice.entities.Rating;

import java.util.List;

public interface RatingService {
    //create rating
    Rating createRating(Rating rating);

    //get rating by user id
    List<Rating> getRatingByUserId(String userId);

    //get all ratings
    List<Rating> getAllRatings();

    //get rating by hotel id
    List<Rating> getRatingByHotelId(String hotelId);
}
