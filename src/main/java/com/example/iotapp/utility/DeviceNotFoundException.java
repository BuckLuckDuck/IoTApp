package com.example.iotapp.utility;

public class DeviceNotFoundException extends RuntimeException{
    public DeviceNotFoundException(String message) {
        super(message);
    }
}
