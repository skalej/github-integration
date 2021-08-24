package com.example.test.web.mapper.github;

import com.example.test.github.model.GitHubCommit;
import com.example.test.web.dto.CommitDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface GitHubCommitMapper {

    CommitDto toCommitDto(GitHubCommit commit);

    List<CommitDto> toCommitDtoList(List<GitHubCommit> commits);
}
