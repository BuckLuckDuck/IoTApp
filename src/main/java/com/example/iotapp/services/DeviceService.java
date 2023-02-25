package com.example.iotapp.services;

import com.example.iotapp.models.Device;
import com.example.iotapp.utility.SecretKey;
import com.example.iotapp.utility.SecretKeyGenerator;
import com.example.iotapp.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        device.setDateOfAdd(LocalDateTime.now());
        deviceRepository.save(device);
        return key;
    }

    // TODO - exception handler
    public Device getInfoAboutDevice(String serialNumber) {
        return deviceRepository.getDeviceBySerialNumber(serialNumber);
    }

    // TODO - exception handler
    public Page<Device> getInfoAboutAllDevice(String type, String date, Integer offset, Integer limit) {
        PageRequest pr = PageRequest.of(offset, limit);

        System.out.println(date + ' ' + type);

        if (type == null && date == null)
            return deviceRepository.findAll(pr);
        else if (type == null)
            return deviceRepository.findAllByDate(date, pr);
        else if (date == null)
            return deviceRepository.findAllByType(type, pr);
        else
            return deviceRepository.findAllByTypeAndDate(type, date, pr);
    }

    public static boolean validateKey(Device device, String key) {
        return SecretKeyGenerator.decodeStr(key, device.getKey());
    }
}
