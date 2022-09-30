package ru.yandex.practicum.filmorate.exceptions;



public class AmptyListException extends RuntimeException {
    public AmptyListException(String s) {
        super(s);
    }
}