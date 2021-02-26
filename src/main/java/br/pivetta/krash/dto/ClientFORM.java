package br.pivetta.krash.dto;

import br.pivetta.krash.model.Client;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ClientFORM {
    @NotNull
    @NotEmpty
    @Length(min = 3)
    private String name;
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String picture;
    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String password;

    public ClientFORM() {
    }

    public Client updateClient(Client client) {
        client.setName(name);
        client.setEmail(email);

        if (!picture.isBlank() && !picture.isEmpty() && !client.getPicture().equals(picture)) {
            client.setPicture(picture);
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPictureUrl(String pictureUrl) {
        this.picture = pictureUrl;
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
