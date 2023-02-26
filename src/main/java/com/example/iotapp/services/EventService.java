package com.example.iotapp.services;

import com.example.iotapp.models.Device;
import com.example.iotapp.models.Event;
import com.example.iotapp.repositories.DeviceRepository;
import com.example.iotapp.repositories.EventRepository;
import com.example.iotapp.utility.DeviceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final DeviceRepository deviceRepository;
    private final ActiveDevicesService activeDevicesService;

    @Autowired
    public EventService(
            EventRepository eventRepository,
            DeviceRepository deviceRepository,
            ActiveDevicesService activeDevicesService
    ) {
        this.eventRepository = eventRepository;
        this.deviceRepository = deviceRepository;
        this.activeDevicesService = activeDevicesService;
    }

    public boolean createEvent(Event event, String serialNumber, String key) {

        Device device = deviceRepository.findDeviceBySerialNumber(serialNumber).orElseThrow(
                () -> new DeviceNotFoundException("Device with serial number " + serialNumber + " not found"));;
        if (!DeviceService.validateKey(device, key))
            return false;

        event.setDevice(device);
        event.setTime_of_add(LocalDateTime.now());
        eventRepository.save(event);

        activeDevicesService.updateTable(device);

        return true;
    }
}
