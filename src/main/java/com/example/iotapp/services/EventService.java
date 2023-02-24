package com.example.iotapp.services;

import com.example.iotapp.models.Device;
import com.example.iotapp.models.Event;
import com.example.iotapp.repositories.DeviceRepository;
import com.example.iotapp.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public EventService(EventRepository eventRepository, DeviceRepository deviceRepository) {
        this.eventRepository = eventRepository;
        this.deviceRepository = deviceRepository;
    }

    public boolean createEvent(Event event, String serialNumber, String key) {

        Device device = deviceRepository.getDeviceBySerialNumber(serialNumber);
        if (!DeviceService.validateKey(device, key))
            return false;

        event.setDevice(device);
        event.setTime_of_add(new Date());
        eventRepository.save(event);

        return true;
    }
}
