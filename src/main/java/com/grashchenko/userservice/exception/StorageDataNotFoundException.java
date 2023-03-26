package com.grashchenko.userservice.exception;

public class StorageDataNotFoundException extends RuntimeException {
    public StorageDataNotFoundException(String message) {
        super(message);
    }
}
