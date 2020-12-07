package br.pivetta.krash.dto;

import br.pivetta.krash.model.Course;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class CourseDTO {
    private Long id;
    private String name;
    private String description;
    private Long creatorId;
    private LocalDateTime createdAt;

    public CourseDTO() {
    }

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.description = course.getDescription();
        this.creatorId = course.getClient().getId();
        this.createdAt = course.getCreatedAt();
    }

    public static Page<CourseDTO> convertPage(Page<Course> courses) {
        return courses.map(CourseDTO::new);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
