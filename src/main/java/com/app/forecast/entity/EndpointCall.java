package com.app.forecast.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "endpoint_calls")
public class EndpointCall {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endpoint_call_seq")
    @SequenceGenerator(name = "endpoint_call_seq", sequenceName = "endpoint_call_seq", allocationSize = 1)
    private Long id;
    private String latitude;
    private String longitude;
    private LocalDateTime requestCallTime;

    public EndpointCall(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.requestCallTime = LocalDateTime.now();
    }
}
