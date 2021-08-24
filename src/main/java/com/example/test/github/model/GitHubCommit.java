package com.example.test.github.model;

import com.example.test.github.config.CommitDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = CommitDeserializer.class)
public class GitHubCommit {
    private String sha;
    private String message;
    private String url;
    private String author;
    private LocalDateTime date;
}
