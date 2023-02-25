package com.example.iotapp.models;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "events")
@TypeDef(name = "json", typeClass = JsonType.class)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Device device;

    @Column(name = "type", nullable = false)
    private String type;

    @Type(type = "json")
    @Column(name = "payload", columnDefinition = "jsonb")
    private Map<String, Object> payload = new HashMap<>();

    @Column(name = "time_of_add")
    private LocalDateTime time_of_add;

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public Event setPayload(Map<String, Object> payload) {
        this.payload = payload;
        return this;
    }

    public LocalDateTime getTime_of_add() {
        return time_of_add;
    }

    public void setTime_of_add(LocalDateTime addTime) {
        this.time_of_add = addTime;
    }
}
