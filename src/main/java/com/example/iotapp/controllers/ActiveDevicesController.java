package com.example.iotapp.controllers;

import com.example.iotapp.models.ActiveDevices;
import com.example.iotapp.services.ActiveDevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ActiveDevicesController {

    private final ActiveDevicesService activeDevicesService;

    @Autowired
    public ActiveDevicesController(ActiveDevicesService activeDevicesService) {
        this.activeDevicesService = activeDevicesService;
    }

    @GetMapping("/active")
    public ResponseEntity<List<ActiveDevices>> getAllActiveDevices() {
        List<ActiveDevices> activeDevices = activeDevicesService.getAllActiveDevices();
        return new ResponseEntity<>(activeDevices, HttpStatus.OK);
    }
}
