package com.example.iotapp.repositories;

import com.example.iotapp.models.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query(value = "SELECT * FROM devices WHERE serial_number = :serialNumber", nativeQuery = true)
    Optional<Device> findDeviceBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query("SELECT d FROM Device d WHERE d.type like %?1%")
    Page<Device> findAllByType(String type, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT * FROM devices WHERE date(date_of_add) = date(CONCAT(:date, '%')) and type like CONCAT(:type, '%')",
            countQuery = "SELECT count(*) FROM devices WHERE date(date_of_add) = date(CONCAT(:date, '%')) and type like CONCAT(:type, '%')")
    Page<Device> findAllByTypeAndDate(@Param("type") String type, @Param("date") String date, Pageable page);
}
