package br.pivetta.krash.repository;

import br.pivetta.krash.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByName(String courseName, Pageable pageable);

    Optional<Course> findByName(String courseName);
}
