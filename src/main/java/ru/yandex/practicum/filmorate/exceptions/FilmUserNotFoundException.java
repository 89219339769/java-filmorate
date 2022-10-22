package ru.yandex.practicum.filmorate.exceptions;

public class FilmUserNotFoundException extends RuntimeException{
    public FilmUserNotFoundException(String message) {
        super(message);
    }
}
