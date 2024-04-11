package com.grad.akemha.exeption;

import com.grad.akemha.exeption.authExceptions.EmailAlreadyExistsException;
import com.grad.akemha.exeption.authExceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@ResponseStatus
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleRegisterApiException(
            EmailAlreadyExistsException exception,
            WebRequest request
    ){
        //first way
//        final ErrorMessage errorMessage = new ErrorMessage();
//        errorMessage.setMessage(exception.getMessage());
//        errorMessage.setStatus(HttpStatus.NOT_ACCEPTABLE);
//        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);

        //another way
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleLoginApiException(UserNotFoundException exception){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
