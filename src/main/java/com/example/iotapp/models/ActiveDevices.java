package com.example.iotapp.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "active_devices")
public class ActiveDevices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    private Device device;

    @Temporal(TemporalType.TIMESTAMP)
    private Date firstActionDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActionDate;
}
