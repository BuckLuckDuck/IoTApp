package com.example.iotapp.models;

import javax.persistence.*;
import java.util.Date;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstActionDate;

    @Column(name = "last_action")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActionDate;

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

    public Date getFirstActionDate() {
        return firstActionDate;
    }

    public void setFirstActionDate(Date firstActionDate) {
        this.firstActionDate = firstActionDate;
    }

    public Date getLastActionDate() {
        return lastActionDate;
    }

    public void setLastActionDate(Date lastActionDate) {
        this.lastActionDate = lastActionDate;
    }
}
