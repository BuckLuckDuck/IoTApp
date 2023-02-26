package com.example.iotapp.repositories;

import com.example.iotapp.models.ActiveDevices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActiveDevicesRepository extends JpaRepository<ActiveDevices, Long> {

    @Query(value = "SELECT * " +
            "FROM active_devices " +
            "WHERE device_id = :id", nativeQuery = true)
    ActiveDevices findByDevice_Id(@Param("id") Long id);

    @Query(value = "SELECT * " +
            "FROM active_devices " +
            "WHERE last_action < now() - INTERVAL '30 minutes'",
            nativeQuery = true)
    List<ActiveDevices> findAllWithExpirationTime();

    @Query(value = "SELECT * " +
            "FROM active_devices", nativeQuery = true)
    Optional<List<ActiveDevices>> getAllActiveDevices();
}
