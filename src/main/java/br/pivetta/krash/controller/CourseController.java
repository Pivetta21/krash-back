package br.pivetta.krash.controller;

import br.pivetta.krash.dto.CourseDTO;
import br.pivetta.krash.dto.CourseFORM;
import br.pivetta.krash.model.Client;
import br.pivetta.krash.model.Course;
import br.pivetta.krash.repository.ClientRepository;
import br.pivetta.krash.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseRepository courseRepository;
    private final ClientRepository clientRepository;

    public CourseController(CourseRepository courseRepository, ClientRepository clientRepository) {
        this.courseRepository = courseRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public Page<CourseDTO> showCourses(
            @RequestParam(required = false) String courseName,
            @PageableDefault(sort = "name") Pageable pageable
    ) {
        Page<Course> courses;

        if (courseName != null) {
            courses = courseRepository.findByName(courseName, pageable);
        } else {
            courses = courseRepository.findAll(pageable);
        }

        return CourseDTO.convertPage(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new CourseDTO(courseOptional.get()));
    }

    @GetMapping("/modules-count/{id}")
    public ResponseEntity<?> getModulesCountByCourseId(@PathVariable Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Long> modulesCount = new HashMap<>();
        modulesCount.put("modules-count", courseRepository.countModulesByCourseId(id));

        return ResponseEntity.ok(modulesCount);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody @Valid CourseFORM courseFORM, UriComponentsBuilder uriBuilder) {
        Optional<Client> client = clientRepository.findById(courseFORM.getCreatorId());

        if (client.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = new Course(client.get(), courseFORM.getName(), courseFORM.getDescription());
        course.setCreatedAt(LocalDateTime.now());

        URI uri = uriBuilder.path("/course/{id}").buildAndExpand(course.getId()).toUri();

        courseRepository.save(course);

        return ResponseEntity.created(uri).body(new CourseDTO(course));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody @Valid CourseFORM courseFORM) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        Optional<Client> clientOptional = clientRepository.findById(courseFORM.getCreatorId());

        if (courseOptional.isEmpty() || clientOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = courseFORM.updateCourse(courseOptional.get());

        return ResponseEntity.ok(new CourseDTO(course));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        courseRepository.delete(courseOptional.get());

        return ResponseEntity.ok().build();
    }
}
