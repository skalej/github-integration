package com.example.test.github.config;

import com.example.test.github.exception.CustomResponseErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class GitHubConfig {

    //-- The default HttpClient used in the RestTemplate is provided by the JDK. It is developed on top of the HttpURLConnection.
    //-- in Java 11 we have java.net.http.HttpClient
    //-- In Spring, the default HTTP client can be changed to Apache’s HttpClient or Square’s OkHttpClient.
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .errorHandler(new CustomResponseErrorHandler())
                .setConnectTimeout(Duration.ofMillis(1000))
                .setReadTimeout(Duration.ofMillis(1000))
                .build();
    }

    @Value("${api.host}")
    private String apiHost;

    @Value("${api.key}")
    private String apiKey;

    @Bean(name = "rapidApiRestTemplate")
    public RestTemplate rapidApiRestTemplate() {
        // ClientHttpRequestFactory: Factory for {@link ClientHttpRequest} objects. Requests are created by the {createRequest(URI, HttpMethod)} method.
        // HttpComponentsClientHttpRequestFactory: {org.springframework.http.client.ClientHttpRequestFactory} implementation that
        //      uses Apache HttpComponents HttpClient to create requests.
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

        // connection timout
        clientHttpRequestFactory.setConnectTimeout(60000);

        // read timeout
        clientHttpRequestFactory.setReadTimeout(60000);

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        // Interceptors
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if(CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        }

        interceptors.add(new CustomClientHttpRequestInterceptor("x-api-key", apiKey));
        interceptors.add(new CustomClientHttpRequestInterceptor("x-api-host", apiHost));
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}

@Slf4j
class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final String headerName;
    private final String headerValue;

    public CustomClientHttpRequestInterceptor(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest,
                                        byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        // add Auth headers
        httpRequest.getHeaders().set(headerName, headerValue);

        // add logger info settings
        logRequestDetails(httpRequest);

        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

    private void logRequestDetails(HttpRequest httpRequest) {
        log.info("Request Headers: {}", httpRequest.getHeaders());
        log.info("Request Method: {}", httpRequest.getMethod());
        log.info("Request URI: {}", httpRequest.getURI());
    }
}
