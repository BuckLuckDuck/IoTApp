package com.example.iotapp.repositories;

import com.example.iotapp.models.Event;
import com.example.iotapp.utility.IEventsOfTypeDevicesCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(nativeQuery = true,
            value = "SELECT MIN(time_of_add) FROM events WHERE device_id = :id")
    LocalDateTime findTimeOfFirstAction(@Param("id")Long id);

    @Query(nativeQuery = true,
            value = "SELECT MAX(time_of_add) FROM events WHERE device_id = :id")
    LocalDateTime findTimeOfLastAction(@Param("id")Long id);

    Page<Event> findAllByDeviceId(Long id, Pageable pageable);

    @Query (nativeQuery = true,
            value = "SELECT * " +
                    "FROM events " +
                    "WHERE device_id = :id " +
                    "AND date(time_of_add) = date(CONCAT(:date, '%'))",
            countQuery = "SELECT count(*) " +
                    "FROM events " +
                    "WHERE device_id = :id " +
                    "AND date(time_of_add) = date(CONCAT(:date, '%'))")
    Page<Event> findAllByDeviceIdAndDate(@Param("id") Long id, @Param("date") String date, PageRequest pr);

    @Query (nativeQuery = true,
            value = "SELECT d.type as deviceType, count(e) as eventsCount " +
                    "FROM events as e " +
                    "LEFT JOIN devices as d on e.device_id = d.id " +
                    "WHERE e.time_of_add >= date(CONCAT(:dateAfter, '%')) " +
                    "AND e.time_of_add < date(CONCAT(:dateBefore, '%')) " +
                    "GROUP BY d.type")
    Optional<List<IEventsOfTypeDevicesCount>> countEventsByDevicesType(
            @Param("dateAfter") String dateAfter,
            @Param("dateBefore") String dateBefore);
}
