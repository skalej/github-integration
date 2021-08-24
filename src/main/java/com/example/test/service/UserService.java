package com.example.test.service;

import com.example.test.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User save(User user);

    User update(Long id, User user);

    void delete(Long id);
}
