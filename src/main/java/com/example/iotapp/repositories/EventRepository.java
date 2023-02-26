package com.example.iotapp.repositories;

import com.example.iotapp.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT MIN(time_of_add) FROM events WHERE device_id = :id", nativeQuery = true)
    LocalDateTime findTimeOfFirstAction(@Param("id")Long id);

    @Query(value = "SELECT MAX(time_of_add) FROM events WHERE device_id = :id", nativeQuery = true)
    LocalDateTime findTimeOfLastAction(@Param("id")Long id);

    Page<Event> findAllByDeviceId(Long id, Pageable pageable);

    @Query (nativeQuery = true,
            value = "SELECT * FROM events WHERE device_id = :id AND date(time_of_add) = date(CONCAT(:date, '%'))",
            countQuery = "SELECT count(*) FROM events WHERE device_id = :id AND date(time_of_add) = date(CONCAT(:date, '%'))")
    Page<Event> findAllByDeviceIdAndDate(@Param("id") Long id, @Param("date") String date, PageRequest pr);
}
