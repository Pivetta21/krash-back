package br.pivetta.krash.repository;

import br.pivetta.krash.model.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    Page<Module> findByCourse_Id(Long courseId, Pageable pageable);

    int countByCourse_Id(Long courseId);
}
