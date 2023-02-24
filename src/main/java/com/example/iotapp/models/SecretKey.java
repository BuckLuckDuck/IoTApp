package com.example.iotapp.models;

public class SecretKey {
    private final String value;

    // TODO - Creation of keys using BCrypt, if Spring security allowed
    public SecretKey() {
        value = String.valueOf((int)(Math.random() * 9999));
    }

    public String getValue() {
        return value;
    }
}
