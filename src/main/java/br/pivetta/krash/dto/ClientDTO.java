package br.pivetta.krash.dto;

import br.pivetta.krash.model.Client;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class ClientDTO {
    private Long id;
    private String email;
    private String name;
    private String pictureUrl;
    private LocalDateTime signUpDate;
    private String permissionName;

    public ClientDTO() {
    }

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.email = client.getEmail();
        this.name = client.getName();
        this.pictureUrl = client.getPictureUrl();
        this.signUpDate = client.getSignUpDate();
        this.permissionName = client.getPermission().getName();
    }

    public static Page<ClientDTO> convertPage(Page<Client> clients) {
        return clients.map(ClientDTO::new);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public LocalDateTime getSignUpDate() {
        return signUpDate;
    }

    public String getPermissionName() {
        return permissionName;
    }
}
