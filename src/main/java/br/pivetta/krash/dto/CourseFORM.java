package br.pivetta.krash.dto;

import br.pivetta.krash.model.Course;

public class CourseFORM {
    private String name;
    private String description;
    private Long clientId;

    public CourseFORM() {
    }

    public Course updateCourse(Course course) {
        course.setName(name);
        course.setDescription(description);

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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
