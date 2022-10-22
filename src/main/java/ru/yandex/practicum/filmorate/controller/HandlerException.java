package ru.yandex.practicum.filmorate.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmUserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e){
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(FilmUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException (final FilmUserNotFoundException e){
        return new ErrorResponse(e.getMessage());
    }

   @ExceptionHandler(Throwable.class)
   @ResponseStatus(HttpStatus. INTERNAL_SERVER_ERROR)
  public ErrorResponse handleThrowable(final RuntimeException e){
       return new ErrorResponse("Непредвиденное исключение " + e.getStackTrace());
  }
}
