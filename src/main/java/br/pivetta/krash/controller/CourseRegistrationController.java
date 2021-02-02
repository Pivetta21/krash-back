package br.pivetta.krash.controller;

import br.pivetta.krash.dto.CourseRegistrationDTO;
import br.pivetta.krash.dto.CourseRegistrationFORM;
import br.pivetta.krash.dto.CourseRegistrationUpdateFORM;
import br.pivetta.krash.model.Client;
import br.pivetta.krash.model.Course;
import br.pivetta.krash.model.CourseRegistration;
import br.pivetta.krash.repository.ClientRepository;
import br.pivetta.krash.repository.CourseRegistrationRepository;
import br.pivetta.krash.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/registration")
public class CourseRegistrationController {
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseRepository courseRepository;
    private final ClientRepository clientRepository;

    public CourseRegistrationController(CourseRegistrationRepository courseRegistrationRepository, CourseRepository courseRepository, ClientRepository clientRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
        this.courseRepository = courseRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public Page<CourseRegistrationDTO> showRegistrations(
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) Long courseId,
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        Page<CourseRegistration> registrations;

        if (clientId != null && courseId != null) {
            registrations = courseRegistrationRepository.findAllByClient_IdAndCourse_Id(clientId, courseId, pageable);
        } else if (clientId != null) {
            registrations = courseRegistrationRepository.findAllByClient_Id(clientId, pageable);
        } else if (courseId != null) {
            registrations = courseRegistrationRepository.findAllByCourse_Id(courseId, pageable);
        } else {
            registrations = courseRegistrationRepository.findAll(pageable);
        }

        return CourseRegistrationDTO.convertPage(registrations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseRegistrationDTO> getRegistrationById(@PathVariable Long id) {
        Optional<CourseRegistration> registrationOptional = courseRegistrationRepository.findById(id);

        if (registrationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new CourseRegistrationDTO(registrationOptional.get()));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CourseRegistrationDTO> createRegistration(@RequestBody CourseRegistrationFORM registrationFORM, UriComponentsBuilder uriBuilder) {
        Optional<Course> courseOptional = courseRepository.findById(registrationFORM.getCourseId());
        Optional<Client> clientOptional = clientRepository.findById(registrationFORM.getClientId());

        if (courseOptional.isEmpty() || clientOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CourseRegistration registration = new CourseRegistration(clientOptional.get(), courseOptional.get());
        registration.setRegisteredAt(LocalDateTime.now());
        courseRegistrationRepository.save(registration);

        URI uri = uriBuilder.path("/registration/{id}").buildAndExpand(registration.getId()).toUri();

        return ResponseEntity.created(uri).body(new CourseRegistrationDTO(registration));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<CourseRegistrationDTO> createRegistration(@PathVariable Long id, @RequestBody CourseRegistrationUpdateFORM registrationFORM) {
        Optional<CourseRegistration> registrationOptional = courseRegistrationRepository.findById(id);

        if (registrationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CourseRegistration registration = registrationFORM.updateRegistration(registrationOptional.get());

        return ResponseEntity.ok(new CourseRegistrationDTO(registration));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistration(@PathVariable Long id) {
        Optional<CourseRegistration> registrationOptional = courseRegistrationRepository.findById(id);

        if (registrationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        courseRegistrationRepository.delete(registrationOptional.get());

        return ResponseEntity.ok().build();
    }
}
