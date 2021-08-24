package com.example.test;

import com.example.test.github.model.GitHubLogin;
import com.example.test.github.model.GitHubRepository;
import com.example.test.github.model.GitHubRepositoryList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializerAndDeserializerTest {
    final String github_response = "{\"items\":[{\"id\":123,\"name\":\"github-test\",\"owner\":{\"id\":1,\"login\":\"kaleji\",\"contributions\":10},\"score\":1,\"created_at\":\"2014-12-20 02:30:00\",\"updated_at\":\"2014-12-20 02:30:00\"}],\"total_count\":30}";

    @Test
    public final void whenSerializing_thenCorrect() throws JsonProcessingException {
        GitHubRepository item = GitHubRepository.builder()
                .id(123)
                .name("github-test")
                .owner(new GitHubLogin(1, "kaleji",  10))
                .score(1)
                .createdAt(LocalDateTime.of(2014, 12, 20, 2, 30))
                .updatedAt(LocalDateTime.of(2014, 12, 20, 2, 30))
                .build();

        GitHubRepositoryList response = GitHubRepositoryList.builder()
                .totalCount(30)
                .items(List.of(item))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        objectMapper.registerModule(module);
        module.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        String json = objectMapper.writeValueAsString(response);
        assertEquals(github_response, json);
    }

    @Test
    public void whenDeserializing_thenCorrect() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        objectMapper.registerModule(module);
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        GitHubRepositoryList response = objectMapper.readValue(github_response, GitHubRepositoryList.class);
        assertEquals(30, response.getTotalCount());
        assertEquals("github-test", response.getItems().get(0).getName());
    }

}
