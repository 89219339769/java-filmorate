package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase()
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userDbStorage;
    private final FilmDbStorage filmDbStorage;


    private User getUser() {
        return User.builder().email("test@mail.ru").login("testLogin").name("testName").birthday(Date.valueOf("1946-08-20").toLocalDate()).friends(new ArrayList<>()).build();
    }

    private Film getFilm() {
        return Film.builder().name("testName").releaseDate(Date.valueOf("1979-04-17").toLocalDate()).description("testDescription").duration(100).rate(4).mpa(Mpa.builder().id(1L).name("G").build()).genres(new ArrayList<>()).likes(new ArrayList<>()).build();
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM LIKES");
        jdbcTemplate.update("DELETE FROM FILM_GENRE");
        jdbcTemplate.update("DELETE FROM FRIENDSHIP");
        jdbcTemplate.update("DELETE FROM USERS");
        jdbcTemplate.update("DELETE FROM FILMS");
        jdbcTemplate.update("ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE FILMS ALTER COLUMN ID RESTART WITH 1");
    }

    @Test
    void testAddUser() {
        User user = getUser();
        User userSaved = userDbStorage.addUser(user);
        user.setId(1L);
        assertEquals(user, userSaved);
    }

    @Test
    void testAddUserWithLoginSpace() {
        User user = getUser();
        user.setLogin("test test");
        user.setLogin("test test");
        assertThrows(DataIntegrityViolationException.class, () -> userDbStorage.addUser(user), "Логин должен быть без пробелов.");
    }

    @Test
    void testAddUserWithEmptyLogin() {
        User user = getUser();
        user.setLogin("");
        assertThrows(DataIntegrityViolationException.class, () -> userDbStorage.addUser(user), "Логин не должен быть пустым.");
    }

    @Test
    void testAddUserWithFutureBirthday() {
        User user = getUser();
        user.setBirthday(Date.valueOf("2122-11-03").toLocalDate());
        assertThrows(DataIntegrityViolationException.class, () -> userDbStorage.addUser(user), "День рождения не может быть в будущем.");
    }

    @Test
    void testaddUserWithEmptyEmail() {
        User user = getUser();
        user.setEmail("");
        assertThrows(DataIntegrityViolationException.class, () -> userDbStorage.addUser(user), "Электронная почта не может быть пустой.");
    }

    @Test
    void testaddUserWithoutAtInEmail() {
        User user = getUser();
        user.setEmail("testmail.ru");
        assertThrows(DataIntegrityViolationException.class, () -> userDbStorage.addUser(user), "Электронная почта должна содержать символ '@'.");
    }

    @Test
    void testUpdateUser() {
        User user = userDbStorage.addUser(getUser());
        user.setLogin("testUpdateLogin");
        assertEquals(user, userDbStorage.updateUser(user));
    }


    @Test
    void testUpdateUnknownUser() {
        User user = getUser();
        user.setId(9999L);
        assertThrows(ObjectNotFoundException.class, () -> userDbStorage.updateUser(user), "Пользователь с id " + user.getId() + " не найден.");
    }

    @Test
    void testFindAllUsers() {
        User user = userDbStorage.addUser(getUser());
        List<User> users = List.of(user);
        assertEquals(users, userDbStorage.findAllUsers());
    }

    @Test
    void testFindUserById() {
        User user = userDbStorage.addUser(getUser());
        User user2 = userDbStorage.addUser(getUser());
        User user3 = userDbStorage.addUser(getUser());
        assertEquals(user, userDbStorage.findUserById(1L));
        assertEquals(user2, userDbStorage.findUserById(2L));
        assertEquals(user3, userDbStorage.findUserById(3L));
    }

    @Test
    void testFindUnknownUser() {
        assertThrows(ObjectNotFoundException.class, () -> userDbStorage.findUserById(9999L), "Пользователь с id " + 9999 + " не найден.");
    }

    @Test
    void testSaveOneUserFriend() {
        userDbStorage.addUser(getUser());
        userDbStorage.addUser(getUser());
        assertTrue(userDbStorage.saveFriend(1L, 2L));
    }

    @Test
    void testSaveUnknownUserFriend() {
        userDbStorage.addUser(getUser());
        assertThrows(ObjectNotFoundException.class, () -> userDbStorage.saveFriend(1L, -1L), "Пользователь с id " + -1L + " не найден.");
    }

    @Test
    void testFindOneUserFriend() {
        userDbStorage.addUser(getUser());
        User user = userDbStorage.addUser(getUser());
        userDbStorage.saveFriend(1L, 2L);
        assertEquals(List.of(user), userDbStorage.findUserFriends(1L));
    }

    @Test
    void testFindEmptyFriendsOfFriend() {
        userDbStorage.addUser(getUser());
        userDbStorage.addUser(getUser());
        userDbStorage.saveFriend(1L, 2L);
        assertEquals(new ArrayList<>(), userDbStorage.findUserFriends(2L));
    }

    @Test
    void testFindTwoUserFriends() {
        userDbStorage.addUser(getUser());
        User user = userDbStorage.addUser(getUser());
        User user2 = userDbStorage.addUser(getUser());
        userDbStorage.saveFriend(1L, 2L);
        userDbStorage.saveFriend(1L, 3L);
        assertEquals(List.of(user, user2), userDbStorage.findUserFriends(1L));
    }

    @Test
    void testFindEmptyCommonFriends() {
        assertEquals(new ArrayList<>(), userDbStorage.findCommonFriends(1L, 2L));
    }

    @Test
    void testFindOneCommonFriend() {
        userDbStorage.addUser(getUser());
        userDbStorage.addUser(getUser());
        User user = userDbStorage.addUser(getUser());
        userDbStorage.saveFriend(1L, 2L);
        userDbStorage.saveFriend(1L, 3L);
        userDbStorage.saveFriend(2L, 3L);
        assertEquals(List.of(user), userDbStorage.findCommonFriends(1L, 2L));
    }

    @Test
    void testDeleteUserFriend() {
        userDbStorage.addUser(getUser());
        userDbStorage.addUser(getUser());
        userDbStorage.saveFriend(1L, 2L);
        assertTrue(userDbStorage.deleteFriend(1L, 2L));
    }

    @Test
    void testFindOneCommonFriendAfterDeletingOfFriend() {
        userDbStorage.addUser(getUser());
        userDbStorage.addUser(getUser());
        User user = userDbStorage.addUser(getUser());
        userDbStorage.saveFriend(1L, 2L);
        userDbStorage.saveFriend(1L, 3L);
        userDbStorage.saveFriend(2L, 3L);
        userDbStorage.deleteFriend(1L, 2L);
        assertEquals(List.of(user), userDbStorage.findCommonFriends(1L, 2L));
    }

    @Test
    void testSaveFilm() {
        Film film = getFilm();
        Film savedFilm = filmDbStorage.addFilm(film);
        film.setId(1L);
        assertEquals(film, savedFilm);
    }

    @Test
    void testSaveFilmWithEmptyName() {
        Film film = getFilm();
        film.setName("");
        assertThrows(DataIntegrityViolationException.class, () -> filmDbStorage.addFilm(film), "Имя не должно быть пустым.");
    }

    @Test
    void testSaveFilmWithLongDescription() {
        Film film = getFilm();
        film.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят " + "разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, " + "который за время «своего отсутствия», стал кандидатом Коломбани.");
        assertThrows(DataIntegrityViolationException.class, () -> filmDbStorage.addFilm(film), "Описание не должно иметь более 200 символов.");
    }

    @Test
    void testSaveFilmWithOldReleaseDate() {
        Film film = getFilm();
        film.setReleaseDate(Date.valueOf("1895-12-27").toLocalDate());
        assertThrows(DataIntegrityViolationException.class, () -> filmDbStorage.addFilm(film), "Дата выхода не может быть раньше 28.12.1895 г.");
    }

    @Test
    void testSaveFilmWithNotPositiveDuration() {
        Film film = getFilm();
        film.setDuration(0);
        assertThrows(DataIntegrityViolationException.class, () -> filmDbStorage.addFilm(film), "Продолжительность должна быть положительной.");
    }

    @Test
    void testSaveFilmWithNullMpa() {
        Film film = getFilm();
        film.setMpa(null);
        assertThrows(NullPointerException.class, () -> filmDbStorage.addFilm(film), "MPA не может быть 'null'.");
    }

    @Test
    void testUpdateFilm() {
        Film film = filmDbStorage.addFilm(getFilm());
        film.setName("testUpdateName");
        assertEquals(film, filmDbStorage.updateFilm(film));
    }

    @Test
    void testUpdateUnknownFilm() {
        Film film = getFilm();
        film.setId(9999L);
        assertThrows(ObjectNotFoundException.class, () -> filmDbStorage.updateFilm(film), "Фильм с id " + film.getId() + " не найден.");
    }

    @Test
    void testFindAllFilms() {
        Film film = filmDbStorage.addFilm(getFilm());
        List<Film> films = List.of(film);
        assertEquals(films, filmDbStorage.findAllFilms());
    }

    @Test
    void testFindFilmById() {
        Film film = filmDbStorage.addFilm(getFilm());
        Film film2 = filmDbStorage.addFilm(getFilm());
        Film film3 = filmDbStorage.addFilm(getFilm());
        assertEquals(film, filmDbStorage.findFilmById(1L));
        assertEquals(film2, filmDbStorage.findFilmById(2L));
        assertEquals(film3, filmDbStorage.findFilmById(3L));
    }

    @Test
    void testFindUnknownFilm() {
        assertThrows(ObjectNotFoundException.class, () -> filmDbStorage.findFilmById(9999L), "Фильм с id " + 9999 + " не найден.");
    }

    @Test
    void testSaveLike() {
        userDbStorage.addUser(getUser());
        filmDbStorage.addFilm(getFilm());
        filmDbStorage.addFilm(getFilm());
        assertTrue(filmDbStorage.saveLike(2L, 1L));
    }

    @Test
    void testFindEmptyPopularFilms() {
        assertEquals(new ArrayList<>(), filmDbStorage.findPopularFilms(10));
    }

    @Test
    void testFindOnePopularFilm() {
        userDbStorage.addUser(getUser());
        filmDbStorage.addFilm(getFilm());
        Film film = filmDbStorage.addFilm(getFilm());
        filmDbStorage.saveLike(2L, 1L);
        film.setLikes(List.of(1L));
        assertEquals(List.of(film), filmDbStorage.findPopularFilms(1));
    }

    @Test
    void testFindTwoPopularFilms() {
        Film film = filmDbStorage.addFilm(getFilm());
        Film film2 = filmDbStorage.addFilm(getFilm());
        assertEquals(List.of(film, film2), filmDbStorage.findPopularFilms(10));
    }

    @Test
    void testDeleteLike() {
        userDbStorage.addUser(getUser());
        filmDbStorage.addFilm(getFilm());
        filmDbStorage.saveLike(1L, 1L);
        assertTrue(filmDbStorage.deleteLike(1L, 1L));
    }

    @Test
    void testDeleteUnknownUserLike() {
        filmDbStorage.addFilm(getFilm());
        assertFalse(filmDbStorage.deleteLike(1L, -2L));
    }
}