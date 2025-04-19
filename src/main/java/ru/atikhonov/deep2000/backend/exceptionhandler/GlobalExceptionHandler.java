package ru.atikhonov.deep2000.backend.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.atikhonov.deep2000.backend.exception.ValidateException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ValidateException e) {
        return new ResponseEntity<>(new AppError(e.getStatus().value(), e.getMessage()), e.getStatus());
    }
}
