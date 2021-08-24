package com.example.test.github.config;

import com.example.test.github.model.GitHubCommit;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommitDeserializer extends StdDeserializer<GitHubCommit> {

    public CommitDeserializer() {
        this(null);
    }

    protected CommitDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public GitHubCommit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        GitHubCommit commit = new GitHubCommit();
        commit.setSha(jsonNode.get("sha").textValue());
        commit.setUrl(jsonNode.get("url").textValue());
        commit.setMessage(jsonNode.get("commit").get("message").textValue());
        commit.setAuthor(jsonNode.get("commit").get("author").get("name").textValue());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        commit.setDate(LocalDateTime.parse(jsonNode.get("commit").get("committer").get("date").textValue(), formatter));
        return commit;
    }
}
