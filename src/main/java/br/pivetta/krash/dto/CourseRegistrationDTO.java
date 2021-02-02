package br.pivetta.krash.dto;

import br.pivetta.krash.model.CourseRegistration;
import br.pivetta.krash.model.CourseStatus;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseRegistrationDTO {
    private Long studentId;
    private Long courseId;
    private LocalDateTime registered_at;
    private LocalDateTime finished_at;
    private CourseStatus courseStatus;
    private BigDecimal rate;

    public CourseRegistrationDTO() {
    }

    public CourseRegistrationDTO(CourseRegistration courseRegistration) {
        this.studentId = courseRegistration.getClient().getId();
        this.courseId = courseRegistration.getCourse().getId();
        this.registered_at = courseRegistration.getRegisteredAt();
        this.finished_at = courseRegistration.getFinishedAt();
        this.courseStatus = courseRegistration.getCourseStatus();
        this.rate = courseRegistration.getRate();
    }

    public static Page<CourseRegistrationDTO> convertPage(Page<CourseRegistration> courseRegistrations) {
        return courseRegistrations.map(CourseRegistrationDTO::new);
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public LocalDateTime getRegistered_at() {
        return registered_at;
    }

    public LocalDateTime getFinished_at() {
        return finished_at;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
