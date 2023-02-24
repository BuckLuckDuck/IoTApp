package com.example.iotapp.models;

import org.postgresql.util.PGobject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Device device;

    @Column(name = "type", nullable = false)
    private String type;

    // TODO
    @Column(name = "payload", columnDefinition = "jsonb")
    private PGobject payload;

    @Column(name = "time_of_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time_of_add;

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

    public PGobject getPayload() {
        return payload;
    }

    public void setPayload(PGobject payload) {
        this.payload = payload;
    }

    public Date getTime_of_add() {
        return time_of_add;
    }

    public void setTime_of_add(Date addTime) {
        this.time_of_add = addTime;
    }
}
