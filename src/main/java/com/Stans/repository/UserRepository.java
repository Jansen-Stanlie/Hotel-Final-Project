package com.Stans.repository;


import com.Stans.model.User;

public interface UserRepository {
    User loginUser(String username, String password);
    void regisUser(User user);
    boolean isValidEmail(String email);
    boolean isValidPassword(String password);
}
