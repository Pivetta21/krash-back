package br.pivetta.krash.repository;

import br.pivetta.krash.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Page<Permission>findByName(String permissionName, Pageable pageable);
}
