package br.pivetta.krash.dto;

import br.pivetta.krash.model.Client;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ClientFORM {
    @NotNull @NotEmpty
    private String email;
    @NotNull @NotEmpty @Length(min = 3)
    private String name;
    @NotNull @NotEmpty @Length(min = 5)
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
