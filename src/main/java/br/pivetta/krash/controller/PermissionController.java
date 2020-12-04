package br.pivetta.krash.controller;

import br.pivetta.krash.model.Permission;
import br.pivetta.krash.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionRepository permissionRepository;

    @Cacheable(value = "permissionsList")
    @GetMapping
    public Page<Permission> getPermissions(
            @RequestParam(required = false) String permissionName,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable
    ) {
        if(permissionName != null) {
            return permissionRepository.findByName(permissionName, pageable);
        }

        return permissionRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        Optional<Permission> permission = permissionRepository.findById(id);

        if(permission.isPresent()) {
            return ResponseEntity.ok(permission.get());
        }

        return ResponseEntity.notFound().build();
    }

    @CacheEvict(value = "permissionsList", allEntries = true)
    @Transactional
    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody @Valid Permission permission, UriComponentsBuilder uriBuilder) {
        permissionRepository.save(permission);

        URI uri = uriBuilder.path("/permission/{id}").buildAndExpand(permission.getId()).toUri();

        return ResponseEntity.created(uri).body(permission);
    }

    @CacheEvict(value = "permissionsList", allEntries = true)
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody @Valid Permission updatedPermission) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);

        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            permission.setName(updatedPermission.getName());

            return ResponseEntity.ok(permission);
        }

        return ResponseEntity.notFound().build();
    }

    @CacheEvict(value = "permissionsList", allEntries = true)
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> dedeletePermission(@PathVariable Long id) {
        Optional<Permission> permission = permissionRepository.findById(id);

        if (permission.isPresent()) {
            permissionRepository.deleteById(id);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
