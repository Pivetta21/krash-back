package br.pivetta.krash.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Course course;
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus = CourseStatus.STARTED;
    private BigDecimal rate;

    public CourseRegistration() {
    }

    public CourseRegistration(Client client, Course course) {
        this.client = client;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
