package br.pivetta.krash.dto;

import br.pivetta.krash.model.CourseRegistration;
import br.pivetta.krash.model.CourseStatus;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public class CourseRegistrationDTO {
    private Long clientId;
    private Long courseId;
    private CourseStatus courseStatus;
    private BigDecimal rate;

    public CourseRegistrationDTO() {
    }

    public CourseRegistrationDTO(CourseRegistration courseRegistration) {
        this.clientId = courseRegistration.getClient().getId();
        this.courseId = courseRegistration.getCourse().getId();
        this.courseStatus = courseRegistration.getCourseStatus();
        this.rate = courseRegistration.getRate();
    }

    public static Page<CourseRegistrationDTO> convertPage(Page<CourseRegistration> courseRegistrations) {
        return courseRegistrations.map(CourseRegistrationDTO::new);
    }

    public Long getStudentId() {
        return clientId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
