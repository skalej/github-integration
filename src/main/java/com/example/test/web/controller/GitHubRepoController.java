package com.example.test.web.controller;

import com.example.test.github.model.GitHubCommit;
import com.example.test.github.model.GitHubLogin;
import com.example.test.github.service.GitHubRestClient;
import com.example.test.web.dto.CommitDto;
import com.example.test.web.dto.ProjectListDto;
import com.example.test.web.dto.UserDto;
import com.example.test.web.mapper.github.GitHubCommitMapper;
import com.example.test.web.mapper.github.GitHubLoginMapper;
import com.example.test.web.mapper.github.GitHubRepositoryListMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/repos")
public class GitHubRepoController {

    private final GitHubRestClient gitHubRestClient;
    private final GitHubRepositoryListMapper repositoryListMapper;
    private final GitHubLoginMapper userMapper;
    private final GitHubCommitMapper commitMapper;

    public GitHubRepoController(GitHubRestClient restClient, GitHubRepositoryListMapper repositoryListMapper, GitHubLoginMapper userMapper, GitHubCommitMapper commitMapper) {
        this.gitHubRestClient = restClient;
        this.repositoryListMapper = repositoryListMapper;
        this.userMapper = userMapper;
        this.commitMapper = commitMapper;
    }

    @GetMapping("/search")
    public ResponseEntity<ProjectListDto> getRepositoriesByName(@RequestParam("repo") String repoName) {
        log.info("search repositories by name {}", repoName);
        var repositories = gitHubRestClient.getRepositoriesByName(repoName);
        return ResponseEntity.ok(repositoryListMapper.toRepositoryListDto(repositories));
    }

    @GetMapping("/{owner}/{repo}/contributors")
    public ResponseEntity<List<UserDto>> getContributors(@PathVariable("owner") String owner, @PathVariable("repo") String repo) {
        log.info("get contributors by owner {} and repo {}", owner, repo);
        List<GitHubLogin> contributors = gitHubRestClient.getContributors(owner, repo);
        return ResponseEntity.ok(userMapper.toUserDtoList(contributors));
    }

    @GetMapping("/{owner}/{repo}/commits")
    public ResponseEntity<List<CommitDto>> getCommits(@PathVariable("owner") String owner, @PathVariable("repo") String repo) {
        log.info("get contributors by owner {} and repo {}", owner, repo);
        List<GitHubCommit> commits = gitHubRestClient.getCommits(owner, repo);
        return ResponseEntity.ok(commitMapper.toCommitDtoList(commits));
    }

}
