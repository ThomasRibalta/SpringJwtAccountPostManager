package dev.thomasrba.um.exception;

public class IncompletePostException extends RuntimeException {
    public IncompletePostException(String message) {
        super(message);
    }
}
