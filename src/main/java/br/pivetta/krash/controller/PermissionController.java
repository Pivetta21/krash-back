package br.pivetta.krash.controller;

import br.pivetta.krash.dto.ClientDTO;
import br.pivetta.krash.dto.PermissionDTO;
import br.pivetta.krash.model.Client;
import br.pivetta.krash.model.Permission;
import br.pivetta.krash.repository.ClientRepository;
import br.pivetta.krash.repository.PermissionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PermissionRepository permissionRepository;
    private final ClientRepository clientRepository;

    public PermissionController(PermissionRepository permissionRepository, ClientRepository clientRepository) {
        this.permissionRepository = permissionRepository;
        this.clientRepository = clientRepository;
    }

    @Cacheable(value = "permissionsList")
    @GetMapping
    public Page<PermissionDTO> showPermissions(@RequestParam(required = false) String permissionName, Pageable pageable) {
        if(permissionName != null) {
            return PermissionDTO.convertPage(permissionRepository.findByName(permissionName, pageable));
        }

        return PermissionDTO.convertPage(permissionRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionDTO> getPermissionById(@PathVariable Long id) {
        Optional<Permission> permission = permissionRepository.findById(id);

        if(permission.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new PermissionDTO(permission.get()));
    }

    @CacheEvict(value = "permissionsList", allEntries = true)
    @Transactional
    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody @Valid PermissionDTO permissionDTO, UriComponentsBuilder uriBuilder) {
        Permission permission = new Permission(permissionDTO.getName());
        permissionRepository.save(permission);

        URI uri = uriBuilder.path("/permission/{id}").buildAndExpand(permission.getId()).toUri();

        return ResponseEntity.created(uri).body(permission);
    }

    @CacheEvict(value = "permissionsList", allEntries = true)
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody @Valid PermissionDTO permissionDTO) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);

        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            permission.setName(permissionDTO.getName());

            return ResponseEntity.ok(permission);
        }

        return ResponseEntity.notFound().build();
    }

    @Transactional
    @PutMapping("/client/{id}")
    public ResponseEntity<ClientDTO> updateClientPermission(@PathVariable Long id, @RequestBody PermissionDTO permissionDTO) {
        Optional<Permission> permissionOptional = permissionRepository.findByName(permissionDTO.getName());
        Optional<Client> optionalClient = clientRepository.findById(id);

        if(permissionOptional.isEmpty() || optionalClient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Client client = optionalClient.get();
        client.setPermission(permissionOptional.get());

        return ResponseEntity.ok(new ClientDTO(client));
    }

    @CacheEvict(value = "permissionsList", allEntries = true)
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable Long id) {
        Optional<Permission> permission = permissionRepository.findById(id);

        if (permission.isPresent()) {
            permissionRepository.deleteById(id);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
