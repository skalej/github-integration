package com.example.test.service;

import com.example.test.model.Project;
import com.example.test.repository.ProjectRepository;
import com.example.test.repository.UserRepository;
import com.example.test.web.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Project> getProjectsByUserId(Long userId) {
        return projectRepository.findByOwnerId(userId);
    }

    @Override
    public Project getByIdAndUserId(Long projectId, Long userId) {
        return projectRepository.findByIdAndOwnerId(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project with Id " + projectId + " for user Id " + userId + " not found"));
    }

    @Override
    public Project save(Long userId, Project project) {
        return userRepository.findById(userId)
                .map(user -> {
                    project.setOwner(user);
                    return projectRepository.save(project);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found for Id " + userId));
    }

    @Override
    public Project update(Long userId, Long projectId, Project project) {
        Project existed = projectRepository.findByIdAndOwnerId(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project with Id " + projectId + " for user Id " + userId + " not found"));

        existed.setName(project.getName());
        existed.setScore(project.getScore());
        return projectRepository.save(existed);
    }

    @Override
    public void delete(Long userId, Long projectId) {
        Project project = projectRepository.findByIdAndOwnerId(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project with Id " + projectId + " for user Id " + userId + " not found"));
        projectRepository.delete(project);
    }
}
