package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Film {
    private Integer id;
    @NotBlank
    private final String name;

    @NotBlank
    @Size(max = 200)
    private final String description;
    @NotNull
    private LocalDate releaseDate;
    @NotNull
    private Integer duration;

    private Mpa mpa;


    private  List<Genre> genres ;

    private  Set<Integer> like;
}