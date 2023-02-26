package com.example.iotapp.utility.exceptions;

public class DeviceAlreadyExistsException extends RuntimeException{
    public DeviceAlreadyExistsException(String message) {
        super(message);
    }
}
