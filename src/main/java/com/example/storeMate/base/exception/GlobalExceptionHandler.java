package com.example.storeMate.base.exception;

import com.example.storeMate.base.util.rsData.RsData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.NotFound.class)
    public ResponseEntity<RsData<Void>> handleNotFound(MemberException.NotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RsData<>("404", e.getMessage()));
    }

    @ExceptionHandler(MemberException.WrongPassword.class)
    public ResponseEntity<RsData<Void>> handleWrongPassword(MemberException.WrongPassword e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new RsData<>("401", e.getMessage()));
    }

    @ExceptionHandler(MemberException.Deleted.class)
    public ResponseEntity<RsData<Void>> handleDeleted(MemberException.Deleted e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new RsData<>("403", e.getMessage()));
    }
}
