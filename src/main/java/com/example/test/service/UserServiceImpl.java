package com.example.test.service;

import com.example.test.model.User;
import com.example.test.repository.UserRepository;
import com.example.test.web.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User by Id " + id + " not found"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        return userRepository.findById(id)
                .map(existed -> {
                    existed.setName(user.getName());
                    return userRepository.save(existed);
                }).orElseThrow(() -> new ResourceNotFoundException("User by Id " + id + " not found"));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
