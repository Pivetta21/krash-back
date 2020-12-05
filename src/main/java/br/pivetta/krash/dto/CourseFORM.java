package br.pivetta.krash.dto;

import br.pivetta.krash.model.Client;
import br.pivetta.krash.model.Course;

import java.util.Optional;

public class CourseFORM {
    private String name;
    private String description;
    private Long creatorId;

    public CourseFORM() {
    }

    public Course updateCourse(Course course, Client client) {
        course.setName(name);
        course.setDescription(description);
        course.setClient(client);

        return course;
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
}