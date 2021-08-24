package com.example.test.web.mapper.github;

import com.example.test.github.model.GitHubLogin;
import com.example.test.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface GitHubLoginMapper {

    @Mappings({@Mapping(target = "name", source = "user.login")})
    UserDto toUserDto(GitHubLogin user);

    List<UserDto> toUserDtoList(List<GitHubLogin> users);
}
