package com.example.test.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {
    private long id;
    private String name;
    private UserDto owner;
    private int score;
    private String createdAt;
    private String updatedAt;
}
