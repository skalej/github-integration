package com.example.test.github.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Slf4j
public class CustomResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(response.getBody());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String errorMessage = bufferedReader.lines().collect(Collectors.joining(" "));
        log.error("Exception with Status Code: {}, Error message: {}", response.getRawStatusCode(), errorMessage);
        if (response.getStatusCode().is4xxClientError()) {
            throw new GitHubClientException(errorMessage);
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new GitHubServerException(errorMessage);
        }
    }
}
