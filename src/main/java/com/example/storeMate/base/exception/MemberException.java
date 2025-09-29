package com.example.storeMate.base.exception;

public class MemberException extends RuntimeException {

    public MemberException(String message) {
        super(message);
    }

    public static class NotFound extends MemberException {
        public NotFound(String massage) {
            super(massage);
        }
    }

    public static class Deleted extends MemberException {
        public Deleted(String massage) {
            super(massage);
        }
    }

    public static class WrongPassword extends MemberException {
        public WrongPassword(String massage) {
            super(massage);
        }
    }
}
