package br.pivetta.krash.repository;

import br.pivetta.krash.model.CourseRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    Page<CourseRegistration> findAllByClient_Id(Long clientId, Pageable pageable);

    Page<CourseRegistration> findAllByCourse_Id(Long courseId, Pageable pageable);

    Page<CourseRegistration> findAllByClient_IdAndCourse_Id(Long clientId, Long courseId, Pageable pageable);
}
