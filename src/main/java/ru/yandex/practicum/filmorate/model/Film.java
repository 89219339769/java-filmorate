package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;


import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.FilmReleaseDateConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Film {
    private Long id;
    @NotBlank(message = "Название фильма не может быть пустым.")
    private String name;
    @FilmReleaseDateConstraint
    private LocalDate releaseDate;
    @Size(max = 200, message = "Максимальная длина описания фильма — 200 символов.")
    private String description;
    @Positive(message = "Продолжительность фильма должна быть положительной.")
    private int duration;
    private Integer rate;
    @NotNull
    private Mpa mpa;
    private List<Genre> genres;
    private List<Long> likes;
}
