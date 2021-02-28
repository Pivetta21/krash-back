package br.pivetta.krash.dto;

import br.pivetta.krash.model.Client;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ClientUpdateFORM {
    private String name;
    private String email;
    private String picture;
    private String password;

    public ClientUpdateFORM() {
    }

    public Client updateClient(Client client) {
        if (!picture.isBlank() && !picture.isEmpty() && !client.getPicture().equals(picture)) {
            client.setPicture(picture);
        }

        if (!name.isBlank() && !name.isEmpty() && !client.getName().equals(name)) {
            client.setName(name);
        }

        if (!password.isEmpty() && !password.isBlank()) {
            String newPassword = new BCryptPasswordEncoder().encode(password);

            if (!client.getPassword().equals(newPassword)) {
                client.setPassword(newPassword);
            }
        }

        return client;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
