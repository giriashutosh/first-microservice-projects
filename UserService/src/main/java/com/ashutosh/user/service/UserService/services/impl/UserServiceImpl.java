package com.ashutosh.user.service.UserService.services.impl;

import com.ashutosh.user.service.UserService.entities.Hotel;
import com.ashutosh.user.service.UserService.entities.Rating;
import com.ashutosh.user.service.UserService.entities.User;
import com.ashutosh.user.service.UserService.repositories.UserRepositry;
import com.ashutosh.user.service.UserService.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepositry userRepositry;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger =   LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        //generate random id or unique id
        String randomId = UUID.randomUUID().toString();
        user.setUserId(randomId);
        return userRepositry.save(user);
    }

    @Override
    public User getUserById(String id) {
        //get user from database
        User user = userRepositry.findById(id).orElseThrow(() -> new RuntimeException("User not found with the given id" + id));
        //http://localhost:8083/api/ratings/user/d0dcb6cd-91e1-4c30-9246-3543e7e53da0
        Rating[] ratingsbyUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/"+user.getUserId(), Rating[].class);
        //logger.info("Ratings for user with id " + id + " are " + ratingsbyUser);
        List<Rating> ratings = Arrays.stream(ratingsbyUser).toList();
        logger.info("Ratings for user with id " + id + " are " + ratings);
        //get hotel for each rating
        List<Rating> ratingList = new ArrayList<>();
        try{
            ratings.forEach(rating -> {
                logger.info("Rating is " + rating.getHotelId());
                Hotel hotel = restTemplate.getForEntity("http://HOTEL-SERVICE/hotel/"+rating.getHotelId(), Hotel.class).getBody();
                logger.info("Hotel for rating with id " + rating.getRatingId() + " is " + hotel);
                rating.setHotel(hotel);
                ratingList.add(rating);

            });
        }
        catch (Exception e){
            logger.error("Error while getting hotel for rating " + e);
        }

        user.setRatings(ratingList);


//        List<Rating> ratingList = ratings.stream().map(rating -> {
//            Hotel hotel = restTemplate.getForObject("http://localhost:8082/api/hotels/"+rating.getHotelId(), Hotel.class);
//            logger.info("Hotel for rating with id " + rating.getRatingId() + " is " + hotel);
//            rating.setHotel(hotel);
//            return rating;
//        }).collect(Collectors.toList());
//        ratings.forEach(rating -> {
//            Hotel hotel = restTemplate.getForObject("http://localhost:8082/api/hotels/"+rating.getHotelId(), Hotel.class);
//            logger.info("Hotel for rating with id " + rating.getRatingId() + " is " + hotel);
//            rating.setHotel(hotel);
//        });
//        user.setRatings(ratings);
        return user;
    }

    @Override
    public List<User> getAllUsers() {

        return userRepositry.findAll();
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }
}
