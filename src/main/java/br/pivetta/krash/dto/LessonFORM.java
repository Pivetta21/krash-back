package br.pivetta.krash.dto;

import br.pivetta.krash.model.Lesson;
import br.pivetta.krash.model.Module;

public class LessonFORM {
    private String name;
    private String description;
    private int number;
    private String videoUri;
    private Long moduleId;

    public LessonFORM() {
    }

    public Lesson updateLesson(Lesson lesson, Module module) {
        lesson.setName(name);
        lesson.setDescription(description);
        lesson.setNumber(number);
        lesson.setVideoUri(videoUri);
        lesson.setModule(module);
        return lesson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
}
