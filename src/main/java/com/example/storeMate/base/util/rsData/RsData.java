package com.example.storeMate.base.util.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RsData<T> {

    private String statusCode;

    private String massage;

    private T data;

    public RsData(String statusCode, String massage) {
        this.statusCode = statusCode;
        this.massage = massage;
    }
}
