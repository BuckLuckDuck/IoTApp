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

    @PostMapping(value = "/device")
    public ResponseEntity<SecretKey> addNewDevice(@RequestBody @NonNull Device device) {
        SecretKey key = deviceService.addNewDevice(device);
        return new ResponseEntity<>(key, HttpStatus.CREATED);
    }

    @GetMapping("/device")
    public ResponseEntity<Device> getInfoAboutDevice(@RequestParam @NonNull String serialNumber) {
        return new ResponseEntity<>(deviceService.getInfoAboutDevice(serialNumber), HttpStatus.OK);
    }

    @GetMapping("/device/all")
    public Page<Device> getInfoAboutAllDevices(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "offset", defaultValue = "0")  Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        return deviceService.getInfoAboutAllDevice(type, date, offset, limit);
    }
}
