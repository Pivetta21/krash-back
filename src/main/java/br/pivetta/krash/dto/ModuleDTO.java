package br.pivetta.krash.dto;

import br.pivetta.krash.model.Module;
import org.springframework.data.domain.Page;

public class ModuleDTO {
    private Long id;
    private Long courseId;
    private String name;
    private int number;

    public ModuleDTO() {
    }

    public ModuleDTO(Module module) {
        this.id = module.getId();
        this.courseId = module.getCourse().getId();
        this.name = module.getName();
        this.number = module.getNumber();
    }

    public static Page<ModuleDTO> convertPage(Page<Module> modules) {
        return modules.map(ModuleDTO::new);
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}
