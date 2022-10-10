package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
public class User {
    private int id;
    private String email;
    private  String login;
    private String name;
    private LocalDate birthday;
    Set<Integer> friends;


}