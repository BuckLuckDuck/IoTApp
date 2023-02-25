package com.example.iotapp.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "active_devices")
public class ActiveDevices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    private Device device;

    @Column(name = "first_action")
    private LocalDateTime firstActionDate;

    @Column(name = "last_action")
    private LocalDateTime lastActionDate;

    public ActiveDevices() {
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

    public LocalDateTime getFirstActionDate() {
        return firstActionDate;
    }

    public void setFirstActionDate(LocalDateTime firstActionDate) {
        this.firstActionDate = firstActionDate;
    }

    public LocalDateTime getLastActionDate() {
        return lastActionDate;
    }

    public void setLastActionDate(LocalDateTime lastActionDate) {
        this.lastActionDate = lastActionDate;
    }
}
