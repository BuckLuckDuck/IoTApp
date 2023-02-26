package com.example.iotapp.services;

import com.example.iotapp.models.Device;
import com.example.iotapp.models.Event;
import com.example.iotapp.repositories.EventRepository;
import com.example.iotapp.utility.IEventsOfTypeDevicesCount;
import com.example.iotapp.utility.exceptions.NothingFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final DeviceService deviceService;
    private final ActiveDevicesService activeDevicesService;

    @Autowired
    public EventService(
            EventRepository eventRepository,
            DeviceService deviceService,
            ActiveDevicesService activeDevicesService
    ) {
        this.eventRepository = eventRepository;
        this.deviceService = deviceService;
        this.activeDevicesService = activeDevicesService;
    }

    public boolean createEvent(Event event, String serialNumber, String key) {

        Device device = deviceService.getDeviceBySerialNumber(serialNumber);

        if (!DeviceService.validateKey(device, key))
            return false;

        event.setDevice(device);
        event.setTime_of_add(LocalDateTime.now());
        eventRepository.save(event);

        activeDevicesService.updateTable(device);

        return true;
    }

    public Page<Event> getAllEventsWithSerialNumber(
            String serialNumber,
            String date,
            Integer offset,
            Integer limit) {
        PageRequest pr = PageRequest.of(offset, limit);
        Device device = deviceService.getDeviceBySerialNumber(serialNumber);
        return date == null ?
                eventRepository.findAllByDeviceId(device.getId(), pr) :
                eventRepository.findAllByDeviceIdAndDate(device.getId(), date, pr);
    }

    public List<IEventsOfTypeDevicesCount> getStatOfEventsByDevicesType(
            String dateAfter, String dateBefore) {
        return eventRepository.countEventsByDevicesType(dateAfter, dateBefore).orElseThrow(
                () -> new NothingFoundException("Nothing found")
        );
    }
}
