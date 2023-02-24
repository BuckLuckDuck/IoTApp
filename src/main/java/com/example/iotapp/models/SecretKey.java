package com.example.iotapp.models;

public class SecretKey {

    private final String key;

    // TODO - Creation of keys using BCrypt, if Spring security allowed
    public SecretKey() {
        key = String.valueOf((int)(Math.random() * 9999));
    }

    public String getKey() {
        return key;
    }
}
