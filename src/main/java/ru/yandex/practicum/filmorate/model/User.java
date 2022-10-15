package ru.yandex.practicum.filmorate.model;

import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
public class User {
    private Integer id;
    @NotBlank @Email
    private  String email;
    private  String login;
    private String name;
    @NotNull
    private  LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();

    public User(Integer id,String email, String name, String login, LocalDate birthday,Set<Integer> friends) {
        this.id = id;
        this.email = email;
        this.login = login;
        if (name.isBlank()){
            this.name = login;
        } else {
            this.name = name;
        }
        this.birthday = birthday;
    }
}