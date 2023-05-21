package com.example.market.service;

import com.example.market.dto.UserSignupDto;
import com.example.market.model.Items;
import com.example.market.model.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.awt.desktop.UserSessionEvent;

public interface UserService extends UserDetailsService {

    Users save(UserSignupDto signupDto);

    Users updateUser(Users users);

    Users findUserByEmail(String email);

    boolean isEmailExist(String email);

    Users getUserById(long user_id);

    void changePassword(long user_id, String newPassword);

}