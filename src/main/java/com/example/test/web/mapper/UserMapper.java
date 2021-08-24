package com.example.test.web.mapper;

import com.example.test.model.User;
import com.example.test.web.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toUser(UserDto userDto);
    UserDto toUserDto(User user);
}
