package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

public class UserEntry extends BaseEntry {
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
    @NotNull
    @Pattern(regexp = "\\S+")
    private String login;
    @Getter
    private String name;

    public UserEntry() {
        friends = new HashSet<>();
    }

    public UserEntry(long id, String email, String login, String name, LocalDate birthday) {
        super(id);
        this.birthday = birthday;
        this.email = email;
        this.login = login;
        this.name = name == null || name.isBlank() ? login : name;
        friends = new HashSet<>();
    }

    public void addFriend(final long friendId) {
        friends.add(friendId);
    }

    public Set<Long> friends() {
        return Set.copyOf(friends);
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

    public void removeFriend(final long friendId) {
        friends.remove(friendId);
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
