package com.example.test.web.controller;

import com.example.test.model.User;
import com.example.test.service.UserService;
import com.example.test.web.dto.UserDto;
import com.example.test.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("getting all users");
        List<UserDto> list = userService.getAllUsers()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        log.info("get user by id {}", id);
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("saving user " + userDto);
        User user = userService.save(userMapper.toUser(userDto));
        return new ResponseEntity<>(userMapper.toUserDto(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
        log.info("updating user with id {} and userDto {}", id, userDto);
        User updated = userService.update(id, userMapper.toUser(userDto));
        return ResponseEntity.ok(userMapper.toUserDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        log.info("deleting user by id {}", id);
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
