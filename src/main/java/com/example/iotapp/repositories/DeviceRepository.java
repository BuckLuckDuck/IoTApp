package com.example.iotapp.repositories;

import com.example.iotapp.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Device getDeviceBySerialNumber(String serialNumber);
}
