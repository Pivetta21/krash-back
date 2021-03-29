package br.pivetta.krash.dto;

import br.pivetta.krash.model.Lesson;
import org.springframework.data.domain.Page;

public class LessonDTO {
    private Long id;
    private Long moduleId;
    private String name;
    private String description;
    private int number;
    private String videoUri;

    public LessonDTO() {
    }

    public LessonDTO(Lesson lesson) {
        this.id = lesson.getId();
        this.moduleId = lesson.getModule().getId();
        this.name = lesson.getName();
        this.description = lesson.getDescription();
        this.number = lesson.getNumber();
        this.videoUri = lesson.getVideoUri();
    }

    public static Page<LessonDTO> convertPage(Page<Lesson> lessons) {
        return lessons.map(LessonDTO::new);
    }

    public Long getId() {
        return id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getVideoUri() {
        return videoUri;
    }
}
