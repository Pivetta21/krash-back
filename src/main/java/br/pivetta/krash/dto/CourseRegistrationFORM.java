package br.pivetta.krash.dto;

public class CourseRegistrationFORM {
    private Long clientId;
    private Long courseId;

    public CourseRegistrationFORM() {
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
