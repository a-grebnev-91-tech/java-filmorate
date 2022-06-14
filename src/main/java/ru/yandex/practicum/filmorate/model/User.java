package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Getter
    @Setter
    @Past
    private LocalDate birthday;
    @Getter
    @Setter
    @NotBlank
    @Email(message = "Email should be valid")
    private String email;
    private final Set<Long> friends;
    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private long id;
    @Getter
    @NotNull
    @Pattern(regexp = "\\S+")
    private String login;
    @Getter
    private String name;

    public User() {
        friends = new HashSet<>();
    }

    public User(long id, String email, String login, String name, LocalDate birthday) {
        this.birthday = birthday;
        this.email = email;
        this.id = id;
        this.login = login;
        this.name = name == null || name.isBlank() ? login : name;
        friends = new HashSet<>();
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
}
