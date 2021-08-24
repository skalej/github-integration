package com.example.test.github.service;

import com.example.test.github.model.GitHubCommit;
import com.example.test.github.model.GitHubRepositoryList;
import com.example.test.github.model.GitHubLogin;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.example.test.github.config.GitHubConstants.*;

@Service
public class GitHubRestClient {

    private final RestTemplate restTemplate;

    public GitHubRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GitHubRepositoryList getRepositoriesByName(String repo) {

        String uriString = UriComponentsBuilder
                .fromUriString(GET_REPOSITORIES_URL)
                .queryParam("q", repo)
                .build()
                .toUriString();

        return restTemplate.getForObject(uriString, GitHubRepositoryList.class);
    }

    public List<GitHubLogin> getContributors(String owner, String repo) {

        ResponseEntity<List<GitHubLogin>> contributorsEntity =
                restTemplate.exchange(GET_CONTRIBUTORS_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<GitHubLogin>>() {
                        },
                        owner,
                        repo);
        return contributorsEntity.getBody();
    }

    public List<GitHubCommit> getCommits(String owner, String repo) {
        ResponseEntity<List<GitHubCommit>> commitsEntity = restTemplate.exchange(GET_COMMITS_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GitHubCommit>>() {
                },
                owner,
                repo);
        return commitsEntity.getBody();
    }


}
