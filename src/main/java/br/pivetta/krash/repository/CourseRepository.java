package br.pivetta.krash.repository;

import br.pivetta.krash.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByName(String courseName, Pageable pageable);

    Optional<Course> findByName(String courseName);

    @Query(value =
            " SELECT count(*) FROM course c" +
            " JOIN module m ON c.id = m.course_id AND m.course_id = :courseId",
            nativeQuery = true)
    int countModulesByCourseId(@Param("courseId") Long courseId);
}
