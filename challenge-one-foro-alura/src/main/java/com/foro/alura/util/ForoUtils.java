package com.foro.alura.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ForoUtils {

    private ForoUtils() {
    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus) {
        return new ResponseEntity<String>( "Mensaje : " + message, httpStatus);
    }
}
