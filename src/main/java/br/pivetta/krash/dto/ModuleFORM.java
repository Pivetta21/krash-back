package br.pivetta.krash.dto;

import br.pivetta.krash.model.Module;

public class ModuleFORM {
    private String name;
    private int number;
    private Long courseId;

    public ModuleFORM() {
    }

    public Module updateModule(Module module) {
        module.setName(name);
        module.setNumber(number);

        return module;
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
