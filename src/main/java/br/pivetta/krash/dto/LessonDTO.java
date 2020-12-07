package br.pivetta.krash.dto;

import br.pivetta.krash.model.Lesson;
import org.springframework.data.domain.Page;

public class LessonDTO {
    private String name;
    private String description;
    private int number;
    private String videoUri;

    public LessonDTO() {
    }

    public LessonDTO(Lesson lesson) {
        this.name = lesson.getName();
        this.description = lesson.getDescription();
        this.number = lesson.getNumber();
        this.videoUri = lesson.getVideoUri();
    }

    public static Page<LessonDTO> convertPage(Page<Lesson> lessons) {
        return lessons.map(LessonDTO::new);
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
