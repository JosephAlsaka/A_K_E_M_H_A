package com.grad.akemha.exception;

import com.grad.akemha.dto.ErrorResponse;
import com.grad.akemha.exception.authExceptions.EmailAlreadyExistsException;
import com.grad.akemha.exception.authExceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleRegisterApiException(
            EmailAlreadyExistsException exception,
            WebRequest request
    ) {
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
    public ResponseEntity<ErrorMessage> handleLoginApiException(UserNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                (new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body
                (new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage()));
    }


    @ExceptionHandler(DeviceAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleDeviceAlreadyExistsException(DeviceAlreadyExistsException exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorMessage);
    }


    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleDeviceNotFoundException(DeviceNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(DeviceReservationNoQuantityException.class)
    public ResponseEntity<ErrorMessage> handleDeviceReservationNoQuantityException(DeviceReservationNoQuantityException exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(DeviceReservationQuantityException.class)
    public ResponseEntity<ErrorMessage> handleDeviceReservationQuantityException(DeviceReservationQuantityException exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY , exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessage);
    }
}
