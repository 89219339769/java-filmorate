package ru.yandex.practicum.filmorate.exceptions;



public class AmptyNameException extends RuntimeException {
    public AmptyNameException(String s) {
        super(s);
    }
}
