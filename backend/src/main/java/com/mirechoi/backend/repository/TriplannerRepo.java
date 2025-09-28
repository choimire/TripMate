package com.mirechoi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirechoi.backend.entity.TripLocation;

public interface TriplannerRepo extends JpaRepository<TripLocation,Long> {

}
