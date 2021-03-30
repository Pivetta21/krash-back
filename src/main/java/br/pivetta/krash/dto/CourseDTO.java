package br.pivetta.krash.dto;

import br.pivetta.krash.model.Client;
import br.pivetta.krash.model.Course;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class CourseDTO {
    private Long id;
    private String name;
    private String description;
    private Client client;
    private String picture;
    private LocalDateTime createdAt;

    public CourseDTO() {
    }

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.description = course.getDescription();
        this.client = course.getClient();
        this.createdAt = course.getCreatedAt();
        this.picture = course.getPicture();
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

    public Client getClient() {
        return client;
    }

    public String getPicture() {
        return picture;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
