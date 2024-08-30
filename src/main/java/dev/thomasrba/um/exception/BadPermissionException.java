package dev.thomasrba.um.exception;

public class BadPermissionException extends RuntimeException {
    public BadPermissionException(String message) {
        super(message);
    }
}
