package br.pivetta.krash.controller;

import br.pivetta.krash.dto.LessonDTO;
import br.pivetta.krash.dto.LessonFORM;
import br.pivetta.krash.model.Lesson;
import br.pivetta.krash.model.Module;
import br.pivetta.krash.repository.LessonRepository;
import br.pivetta.krash.repository.ModuleRepository;
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
@RequestMapping("/lesson")
public class LessonController {
    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    public LessonController(LessonRepository lessonRepository, ModuleRepository moduleRepository) {
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
    }

    @GetMapping
    public Page<LessonDTO> showLessons(Pageable pageable) {
        Page<Lesson> lessons = lessonRepository.findAll(pageable);

        return LessonDTO.convertPage(lessons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);

        if (lessonOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new LessonDTO(lessonOptional.get()));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<LessonDTO> createLesson(@RequestBody @Valid LessonFORM lessonFORM, UriComponentsBuilder uriBuilder) {
        Optional<Module> moduleOptional = moduleRepository.findById(lessonFORM.getModuleId());

        if (moduleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Lesson lesson = new Lesson(lessonFORM.getName(), lessonFORM.getDescription(), lessonFORM.getNumber(), lessonFORM.getVideoUri(), moduleOptional.get());
        lessonRepository.save(lesson);

        URI uri = uriBuilder.path("/lesson/{id}").buildAndExpand(lesson.getId()).toUri();

        return ResponseEntity.created(uri).body(new LessonDTO(lesson));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long id, @RequestBody @Valid LessonFORM lessonFORM) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        Optional<Module> moduleOptional = moduleRepository.findById(lessonFORM.getModuleId());

        if (lessonOptional.isEmpty() || moduleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Lesson lesson = lessonFORM.updateLesson(lessonOptional.get(), moduleOptional.get());

        return ResponseEntity.ok(new LessonDTO(lesson));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);

        if (lessonOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        lessonRepository.delete(lessonOptional.get());

        return ResponseEntity.ok().build();
    }
}
