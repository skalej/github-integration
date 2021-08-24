package com.example.test.web.mapper.github;

import com.example.test.github.model.GitHubRepositoryList;
import com.example.test.web.dto.ProjectListDto;
import org.mapstruct.Mapper;

@Mapper (uses = GitHubLoginMapper.class)
public interface GitHubRepositoryListMapper {
    ProjectListDto toRepositoryListDto(GitHubRepositoryList repositoryResponse);
}
