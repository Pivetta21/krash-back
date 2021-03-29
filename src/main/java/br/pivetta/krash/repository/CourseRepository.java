package br.pivetta.krash.repository;

import br.pivetta.krash.model.ContentCount;
import br.pivetta.krash.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAllByName(String courseName, Pageable pageable);

    Page<Course> findAllByClientId(Long id, Pageable pageable);

    Optional<Course> findByName(String courseName);

    @Query(value = "" +
            "SELECT 'modules' AS content, count(*) AS count " +
            "FROM module m " +
            "WHERE m.course_id = :courseId " +
            "UNION ALL " +
            "SELECT 'lessons' AS content, count(*) AS count " +
            "FROM module m " +
            "JOIN lesson l ON m.id = l.module_id AND m.course_id = :courseId"
            , nativeQuery = true)
    List<ContentCount> contentCountByCourseId(@Param("courseId") Long courseId);
}
