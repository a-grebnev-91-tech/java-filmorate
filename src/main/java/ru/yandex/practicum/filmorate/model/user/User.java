package ru.yandex.practicum.filmorate.model.user;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class User {
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    @Past
    private LocalDate birthday;
    @Getter
    @Setter
    @NotBlank
    @Email(message = "Email should be valid")
    private String email;
    @Getter
    @NotNull
    @Pattern(regexp = "\\S+")
    private String login;
    @Getter
    private String name;

    public User() {}

    public User(long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.birthday = birthday;
        this.email = email;
        this.login = login;
        this.name = name == null || name.isBlank() ? login : name;
    }

    public void setLogin(String login) {
        this.login = login;
        if (this.name == null)
            this.name = login;
    }

    public void setName(String name) {
        if (name == null || name.isBlank())
            this.name = this.login;
        else
            this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                "birthday=" + birthday +
                '}';
    }
}
