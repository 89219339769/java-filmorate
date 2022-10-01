
package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@Builder
@Data
public class Film {
    int id;
    // @NotBlank


    String name;
    //   @Size(max = 300)
    String description;


    LocalDate releaseDate;
    //   @Positive
    Duration duration;


    int rate;
}
