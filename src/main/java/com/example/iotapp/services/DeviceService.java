package com.example.iotapp.services;

import com.example.iotapp.models.Device;
import com.example.iotapp.models.SecretKey;
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

    public SecretKey addNewDevice(Device device) {
        // TODO - should write special GlobalExceptionHandler
        if (deviceRepository.getDeviceBySerialNumber(device.getSerialNumber()) != null)
            throw new IllegalArgumentException();

        SecretKey newKey = new SecretKey();
        device.setKey(newKey.getKey());

        device.setDateOfAdd(new Date());
        deviceRepository.save(device);
        return newKey;
    }

    public static boolean validateKey(Device device, String key) {
        if (device.getKey().equals(key))
            return true;
        return false;
    }
}
