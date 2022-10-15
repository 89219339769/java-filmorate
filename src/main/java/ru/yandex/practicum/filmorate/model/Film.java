package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
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
    private Set<Integer> like;

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration, Set<Integer> like) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.like = like;
        if (like == null) {
            this.like = new HashSet<>();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Integer> getLike() {
        return like;
    }

    public void setLike(int idUser) {
        this.like.add(idUser);
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
