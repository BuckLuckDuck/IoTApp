package com.example.iotapp.services;

import com.example.iotapp.models.Device;
import com.example.iotapp.utility.SecretKey;
import com.example.iotapp.utility.SecretKeyGenerator;
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


        SecretKeyGenerator keyGenerator = new SecretKeyGenerator();
        SecretKey key = new SecretKey();
        device.setKey(keyGenerator.encodeStr(key.getKEY()));

        device.setDateOfAdd(new Date());
        deviceRepository.save(device);
        return key;
    }

    public Device getInfoAboutDevice(String serialNumber) {
        return deviceRepository.getDeviceBySerialNumber(serialNumber);
    }

    public static boolean validateKey(Device device, String key) {
        return SecretKeyGenerator.decodeStr(key, device.getKey());
    }
}
