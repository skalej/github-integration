package com.example.test.web.controller;

import com.example.test.model.Project;
import com.example.test.service.ProjectService;
import com.example.test.web.dto.ProjectDto;
import com.example.test.web.mapper.ProjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/v1")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @GetMapping("/users/{userId}/projects")
    public ResponseEntity<List<ProjectDto>> getProjectsByUserId(@PathVariable("userId") Long userId) {
        log.info("getting project by userId {}", userId);
        List<ProjectDto> list = projectService.getProjectsByUserId(userId).stream()
                .map(projectMapper::toProjectDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping("/users/{userId}/projects")
    public ResponseEntity<ProjectDto> createProject(@PathVariable("userId") Long userId,
                                                    @Valid @RequestBody ProjectDto projectDto) {
        log.info("creating new project for user Id {}, project {}", userId, projectDto );
        Project project = projectService.save(userId, projectMapper.toProject(projectDto));
        return new ResponseEntity<>(projectMapper.toProjectDto(project), HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/projects/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable("userId") Long userId,
                                                    @PathVariable("id") Long projectId) {
        log.info("getting project by Id {} for user Id {}", projectId,  userId);
        Project project = projectService.getByIdAndUserId(projectId, userId);
        return ResponseEntity.ok(projectMapper.toProjectDto(project));
    }

    @PutMapping("/users/{userId}/projects/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable("userId") Long userId,
                                                    @PathVariable("id") Long projectId,
                                                    @Valid @RequestBody ProjectDto projectDto) {
        log.info("updating project by Id {} for user Id {}, project {}", projectId,  userId, projectDto );
        Project updated = projectService.update(userId, projectId, projectMapper.toProject(projectDto));
        return ResponseEntity.ok(projectMapper.toProjectDto(updated));
    }

    @DeleteMapping("/users/{userId}/projects/{id}")
    public ResponseEntity<?> updateProject(@PathVariable("userId") Long userId,
                                           @PathVariable("id") Long projectId) {
        log.info("deleting project by Id {} for user Id {}", projectId,  userId);
        projectService.delete(userId, projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
