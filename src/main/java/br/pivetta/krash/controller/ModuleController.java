package br.pivetta.krash.controller;

import br.pivetta.krash.dto.ModuleDTO;
import br.pivetta.krash.dto.ModuleFORM;
import br.pivetta.krash.model.Course;
import br.pivetta.krash.model.Module;
import br.pivetta.krash.repository.CourseRepository;
import br.pivetta.krash.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/module")
public class ModuleController {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public Page<ModuleDTO> showModules(@RequestParam(required = false) Long courseId, Pageable pageable) {
        Page<Module> modules;

        if (courseId != null) {
            modules = moduleRepository.findByCourse_Id(courseId, pageable);
        } else {
            modules = moduleRepository.findAll(pageable);
        }

        return ModuleDTO.convertPage(modules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleDTO> getModuleById(@PathVariable Long id) {
        Optional<Module> moduleOptional = moduleRepository.findById(id);

        if (moduleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ModuleDTO(moduleOptional.get()));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ModuleDTO> createModule(@RequestBody @Valid ModuleFORM moduleFORM, UriComponentsBuilder uriBuilder) {
        Optional<Course> optionalCourse = courseRepository.findById(moduleFORM.getCourseId());

        if (optionalCourse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Module module = new Module(moduleFORM.getName(), moduleFORM.getNumber(), optionalCourse.get());
        moduleRepository.save(module);

        URI uri = uriBuilder.path("/module/{id}").buildAndExpand(module.getId()).toUri();

        return ResponseEntity.created(uri).body(new ModuleDTO(module));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ModuleDTO> updateModule(@PathVariable Long id, @RequestBody @Valid ModuleFORM moduleFORM) {
        Optional<Course> optionalCourse = courseRepository.findById(moduleFORM.getCourseId());
        Optional<Module> optionalModule = moduleRepository.findById(id);

        if (optionalCourse.isEmpty() || optionalModule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Module module = moduleFORM.updateModule(optionalModule.get());

        return ResponseEntity.ok(new ModuleDTO(module));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<ModuleDTO> deleteModule(@PathVariable Long id) {
        Optional<Module> optionalModule = moduleRepository.findById(id);

        if (optionalModule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        moduleRepository.delete(optionalModule.get());

        return ResponseEntity.ok().build();
    }
}
