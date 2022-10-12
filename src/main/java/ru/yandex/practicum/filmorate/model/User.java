package ru.yandex.practicum.filmorate.model;

import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
public class User {
    private Integer id;
    @NotBlank @Email
    private  String email;
   // @NotBlank @Pattern(regexp = "\\S+")
    private  String login;
    private String name;
    @NotNull
    private  LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();

    public User(String email, String name, String login, LocalDate birthday) {
        this.email = email;
        this.login = login;
        if (name.isBlank()){
            this.name = login;
        } else {
            this.name = name;
        }
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setFriends(Integer friends) {
        this.friends.add(friends);
    }

    public Set<Integer> getFriends() {
        return friends;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}