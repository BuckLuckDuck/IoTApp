package com.example.iotapp.controllers;

import com.example.iotapp.models.Device;
import com.example.iotapp.utility.SecretKey;
import com.example.iotapp.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * Required by the task: Добавление нового устройства
     * Ways that could be used: JWT token
     * @param device
     * @return Device's key
     */
    @PostMapping(value = "/device")
    public SecretKey addNewDevice(@RequestBody @NonNull Device device) {
        return deviceService.addNewDevice(device);
    }

    /**
     * Required by the task: Получить информацию о конкретном устройстве по серийному номеру
     * @param serialNumber
     * @return Device
     */
    @GetMapping("/device")
    public Device getInfoAboutDevice(@RequestParam("serialNumber") String serialNumber) {
        return deviceService.getInfoAboutDevice(serialNumber);
    }

    /**
     * Required by the task:  Получить информацию о всех добавленных в систему устройств
     * @param type
     * @param date
     * @param offset
     * @param limit
     * @return Page that Frontends could use for pagination
     */
    @GetMapping("/device/all")
    public Page<Device> getInfoAboutAllDevices(
            @RequestParam(value = "type", required = false, defaultValue = "%") String type,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "offset", defaultValue = "0")  Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        return deviceService.getInfoAboutAllDevice(type, date, offset, limit);
    }
}
