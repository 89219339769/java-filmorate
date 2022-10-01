package ru.yandex.practicum.filmorate.exceptions;



public class LoginException extends RuntimeException {
    public LoginException(String s) {
        super(s);
    }
}