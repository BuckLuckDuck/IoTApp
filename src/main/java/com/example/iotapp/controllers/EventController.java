package com.example.iotapp.controllers;

import com.example.iotapp.models.Event;
import com.example.iotapp.services.EventService;
import com.example.iotapp.utility.IEventsOfTypeDevicesCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Required by the task: Сохранение события от IoT устройства
     * @param event
     * @param serialNumber
     * @param key
     * @return Empty body with status code
     */
    @PostMapping("/events")
    public ResponseEntity<?> createEvent(
            @RequestBody Event event,
            @RequestParam @NonNull String serialNumber,
            @RequestParam @NonNull String key) {

        boolean created = eventService.createEvent(event, serialNumber, key);

        HttpStatus status = created ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(null, status);
    }

    /**
     * Required by the task: Получить информацию о событиях конкретного устройства по серийному номеру
     * @param serialNumber
     * @param date
     * @param offset
     * @param limit
     * @return Page that Frontends could use for pagination
     */
    @GetMapping("/events")
    public Page<Event> getAllEventsWithSerialNumber(
            @RequestParam("serialNumber") String serialNumber,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        return eventService.getAllEventsWithSerialNumber(serialNumber, date, offset, limit);
    }

    /**
     * Required by the task: Получить статистику по указанному периоду времени о количестве полученных событий,
     * сгруппированном по типам устройств
     * @param dateAfter
     * @param dateBefore
     * @return Statistic
     */
    @GetMapping("/events/statistics")
    public List<IEventsOfTypeDevicesCount> getStatOfEventsByDevicesType(
            @RequestParam(value = "dateAfter",required = false, defaultValue = "100-12-31") String dateAfter,
            @RequestParam(value = "dateBefore", required = false, defaultValue = "99999-01-01") String dateBefore) {
        return eventService.getStatOfEventsByDevicesType(dateAfter, dateBefore);
    }
}
