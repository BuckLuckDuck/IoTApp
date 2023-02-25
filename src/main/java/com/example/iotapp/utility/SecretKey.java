package com.example.iotapp.utility;

public class SecretKey {
    private final String KEY;

    public SecretKey() {
        this.KEY = SecretKeyGenerator.generate(12);
    }

    public String getKEY() {
        return KEY;
    }
}
