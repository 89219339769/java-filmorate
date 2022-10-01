
package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class User {
    int id;
    String email;
    String login;
    String name;
    LocalDate birthday;

  //  public User(int i, String s, String dfdf, String sdsd, LocalDate of) {
  //  }
}
