package ru.yandex.practicum.filmorate.exceptions;



public class InvalidIdException extends RuntimeException {
    public InvalidIdException(String s) {
        super(s);
    }
}