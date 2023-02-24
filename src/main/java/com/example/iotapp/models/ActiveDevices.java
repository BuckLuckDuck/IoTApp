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
}
