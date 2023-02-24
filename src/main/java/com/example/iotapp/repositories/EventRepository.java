package com.example.iotapp.repositories;

import com.example.iotapp.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
