package br.pivetta.krash.dto;

import br.pivetta.krash.model.Client;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ClientFORM {
    private String email;
    private String name;
    private String password;

    public ClientFORM() {
    }

    public Client updateClient(Client client) {
        client.setEmail(email);
        client.setName(name);
        client.setPassword(new BCryptPasswordEncoder().encode(password));

        return client;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
