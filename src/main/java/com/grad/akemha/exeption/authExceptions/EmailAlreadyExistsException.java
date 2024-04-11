package com.grad.akemha.exeption.authExceptions;

public class EmailAlreadyExistsException extends RegistrationException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}