package com.example.iotapp.controllers;

import com.example.iotapp.models.Device;
import com.example.iotapp.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping(value = "/device")
    public ResponseEntity<String> addNewDevice(@RequestBody Device device) {
        String key = deviceService.addNewDevice(device);

        return new ResponseEntity<>(key, HttpStatus.CREATED);
    }
}
