package br.pivetta.krash.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Course course;
    private int number;
    private String name;
    @OneToMany(mappedBy = "module")
    private List<Lesson> lessons;

    public Module() {
    }

    public Module(String name, int number, Course course) {
        this.name = name;
        this.number = number;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
