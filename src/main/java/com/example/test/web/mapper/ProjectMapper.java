package com.example.test.web.mapper;

import com.example.test.model.Project;
import com.example.test.web.dto.ProjectDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectMapper {
    Project toProject(ProjectDto projectDto);
    ProjectDto toProjectDto(Project project);
}
