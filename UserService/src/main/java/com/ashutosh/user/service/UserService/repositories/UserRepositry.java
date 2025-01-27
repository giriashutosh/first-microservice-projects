package com.ashutosh.user.service.UserService.repositories;

import com.ashutosh.user.service.UserService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositry extends JpaRepository<User, String> {
}
