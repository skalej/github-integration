package com.example.test.service;

import com.example.test.model.Project;
import com.example.test.web.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    List<Project> getProjectsByUserId(Long userId);

    Project getByIdAndUserId(Long projectId, Long userId);

    Project save(Long userId, Project project);

    Project update(Long userId, Long projectId, Project project);

    void delete(Long userId, Long projectId);
}
