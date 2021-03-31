package br.pivetta.krash.repository;

import br.pivetta.krash.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Page<Lesson> findByModule_Id(Long moduleId, Pageable pageable);

    Page<Lesson> findByModule_Course_Id(Long courseId, Pageable pageable);

    void deleteByModule_CourseId(Long courseId);
}
