package com.example.test.repository;

import com.example.test.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwnerId(Long userId);
    Optional<Project> findByIdAndOwnerId(Long projectId, Long userId);
}
