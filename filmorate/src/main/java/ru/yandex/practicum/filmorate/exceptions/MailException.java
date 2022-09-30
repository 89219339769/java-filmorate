package ru.yandex.practicum.filmorate.exceptions;



public class MailException extends RuntimeException {
    public MailException(String s) {
        super(s);
    }
}