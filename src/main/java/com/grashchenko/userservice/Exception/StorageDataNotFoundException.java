package com.grashchenko.userservice.Exception;

public class StorageDataNotFoundException extends RuntimeException {
    public StorageDataNotFoundException(String message) {
        super(message);
    }
}
