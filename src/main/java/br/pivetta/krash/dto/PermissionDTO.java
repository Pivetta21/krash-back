package br.pivetta.krash.dto;

import br.pivetta.krash.model.Permission;
import org.springframework.data.domain.Page;

public class PermissionDTO {
    private String name;

    public PermissionDTO(){
    }

    public PermissionDTO(Permission permission) {
        this.name = permission.getName();
    }

    public static Page<PermissionDTO> convertPage(Page<Permission> permissionPage) {
        return permissionPage.map(PermissionDTO::new);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
