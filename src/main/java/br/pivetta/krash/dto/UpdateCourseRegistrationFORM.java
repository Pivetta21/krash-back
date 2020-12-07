package br.pivetta.krash.dto;

import br.pivetta.krash.model.CourseRegistration;
import br.pivetta.krash.model.CourseStatus;

import java.math.BigDecimal;

public class UpdateCourseRegistrationFORM {
    private String courseStatus;
    private BigDecimal rate;

    public UpdateCourseRegistrationFORM() {
    }

    public CourseRegistration updateRegistration(CourseRegistration courseRegistration) {
        courseRegistration.setCourseStatus(CourseStatus.valueOf(courseStatus));
        courseRegistration.setRate(rate);

        return courseRegistration;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
