package ru.kuleshov.restserviceinfostudents.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
