package ru.yandex.practicum.filmorate.exceptions;



public class NegativeDurationException extends RuntimeException {
    public NegativeDurationException(String s) {
        super(s);
    }
}