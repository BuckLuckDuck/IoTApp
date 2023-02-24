package com.example.iotapp.services;

import com.example.iotapp.models.Device;
import com.example.iotapp.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public String addNewDevice(Device device) {
        if (deviceRepository.getDeviceBySerialNumber(device.getSerialNumber()) != null)
            throw new IllegalArgumentException();

        String key = createSecretKey();
        device.setKey(key);

        device.setDateOfAdd(new Date());
        deviceRepository.save(device);
        return device.getKey();
    }

    // TODO - Creation of keys using BCrypt, if Spring security allowed
    private String createSecretKey() {
        int key = (int) (Math.random() * 9999);
        return String.valueOf(key);
    }
}
