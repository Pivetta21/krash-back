package br.pivetta.krash.dto;

import br.pivetta.krash.model.Course;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class CourseDTO {
    private String name;
    private String description;
    private Long creatorId;
    private LocalDateTime createdAt;

    public CourseDTO() {
    }

    public CourseDTO(Course course) {
        this.name = course.getName();
        this.description = course.getDescription();
        this.creatorId = course.getClient().getId();
        this.createdAt = course.getCreatedAt();
    }

    public static Page<CourseDTO> convertPage(Page<Course> courses) {
        return courses.map(CourseDTO::new);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
