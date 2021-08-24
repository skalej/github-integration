package com.example.test.github.exception;

public class GitHubServerException extends RuntimeException {
    public GitHubServerException(String message) {
        super(message);
    }
}
