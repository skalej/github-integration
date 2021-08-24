package com.example.test.web.controller;

import com.example.test.model.User;
import com.example.test.service.UserService;
import com.example.test.web.dto.UserDto;
import com.example.test.web.exception.ResourceNotFoundException;
import com.example.test.web.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserMapper userMapper;

    @MockBean
    UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getUserById() throws Exception {
        User user = new User(1L, "user1");
        when(userService.getUserById(anyLong()))
                .thenReturn(user);
        when(userMapper.toUserDto(user))
                .thenReturn(UserDto.builder().id(1L).name("user1").build());

        mockMvc.perform(get("/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("user1"));
    }

    @Test
    void getUserById_NotFound() throws Exception {
        when(userService.getUserById(anyLong()))
                .thenThrow(new ResourceNotFoundException("error"));

        mockMvc.perform(get("/v1/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser() throws Exception {
        User user = new User(1L, "user1");
        String json = objectMapper.writeValueAsString(user);

        when(userService.save(isA(User.class)))
                .thenReturn(user);

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void createUser_BadRequest() throws Exception {
        User user = new User(1L, "");
        String json = objectMapper.writeValueAsString(user);

        when(userService.save(isA(User.class)))
                .thenReturn(user);

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

    }


}