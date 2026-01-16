package com.example.storeMate.base.exception;

public class BoardException extends RuntimeException {
    public BoardException(String message) {
        super(message);
    }

    public static class Forbidden extends BoardException {

        public Forbidden(String massage) {
            super(massage);
        }
    }

    public static class NotFound extends BoardException {

        public NotFound(String massage) {
            super(massage);
        }
    }
}
