package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;

    private Set<User> friends = new HashSet<>();

    @Email
    @NonNull
    @NotBlank
    private String email;

    @NonNull
    @NotBlank
    @Pattern(regexp = "\\S+")
    private String login;

    private String name;

    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate birthday;

    public User(Integer id, @Valid String email, @Valid String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name.isEmpty() || name.isBlank() ? login : name;
        this.birthday = birthday;
        this.friends = new HashSet<>();
    }

    public void addFriend(User user) {
        this.friends.add(user);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id &&
                id.equals(user.id);
    }


}







