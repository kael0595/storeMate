package com.example.storeMate.base.exception;

public class ProductException extends RuntimeException {
    public ProductException(String message) {
        super(message);
    }

    public static class NotFound extends ProductException {

      public NotFound(String message) {
        super(message);
      }
    }

    public static class Forbidden extends ProductException {

        public Forbidden(String massage) {
            super(massage);
        }
    }
}
