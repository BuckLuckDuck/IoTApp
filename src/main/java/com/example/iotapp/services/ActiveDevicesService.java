package com.example.iotapp.services;

import com.example.iotapp.models.ActiveDevices;
import com.example.iotapp.models.Device;
import com.example.iotapp.repositories.ActiveDevicesRepository;
import com.example.iotapp.repositories.EventRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActiveDevicesService {
    private final ActiveDevicesRepository activeDevicesRepository;
    private final EventRepository eventRepository;

    public ActiveDevicesService(
            ActiveDevicesRepository activeDevicesRepository,
            EventRepository eventRepository
    ) {
        this.activeDevicesRepository = activeDevicesRepository;
        this.eventRepository = eventRepository;
    }

    public void updateTable(Device device) {

        ActiveDevices activeDevice = activeDevicesRepository.findByDevice_Id(device.getId());
        if (activeDevice != null) {
            Date lastAction = eventRepository.findTimeOfLastAction(device.getId());
            activeDevice.setLastActionDate(lastAction);
            activeDevicesRepository.save(activeDevice);
            return;
        }

        Date firstAction = eventRepository.findTimeOfFirstAction(device.getId());
        Date lastAction = eventRepository.findTimeOfLastAction(device.getId());

        ActiveDevices newActiveDevice = new ActiveDevices();
        newActiveDevice.setDevice(device);
        newActiveDevice.setFirstActionDate(firstAction);
        newActiveDevice.setLastActionDate(lastAction);

        activeDevicesRepository.save(newActiveDevice);
    }

    @Scheduled(cron = "10 * * * * ?")
    public final void updateActiveDevices() {
        List<ActiveDevices> listOfInactiveDevices =
                activeDevicesRepository.findAllWithExpirationTime();

        activeDevicesRepository.deleteAll(listOfInactiveDevices);
    }

}