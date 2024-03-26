package com.ashutosh.user.service.UserService.controllers;

import com.ashutosh.user.service.UserService.entities.User;
import com.ashutosh.user.service.UserService.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);
    //create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = (userService.saveUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //get user by id
    @GetMapping("/{id}")
    //@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    //@Retry(name = "retryService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "ratingHotelLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUserById(@PathVariable  String id){
        User user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    //creating fallback method
    public ResponseEntity<User> ratingHotelFallback(String id, Exception e){
        logger.info("Fallback method called for ratingHotelBreaker with exception: "+e.getMessage());
        User user = User.builder()
                .name("Fallback-User")
                .email("fallback@gmail.com")
                .about("Fallback-User-About")
                .userId("fallback-user-id")
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
    }

    //get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
