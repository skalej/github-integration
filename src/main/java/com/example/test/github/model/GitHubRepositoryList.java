package com.example.test.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GitHubRepositoryList {
    @JsonProperty("total_count")
    private int totalCount;
    private List<GitHubRepository> items;
}
