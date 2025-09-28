package com.mirechoi.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "trip_locations")
@Data
public class TripLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; //여행지
    private String airport;//공항
    private int day; //몇번째 날
    private String accommodation; //숙소
    private String address; //주소

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

}
