package com.example.iotapp.repositories;

import com.example.iotapp.models.ActiveDevices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActiveDevicesRepository extends JpaRepository<ActiveDevices, Long> {

    ActiveDevices findByDevice_Id(Long id);

    @Query(value = "SELECT * from active_devices WHERE last_action < now() - INTERVAL '30 minutes'",
            nativeQuery = true)
    List<ActiveDevices> findAllWithExpirationTime();
}
