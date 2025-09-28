package com.mirechoi.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mirechoi.backend.entity.TripLocation;
import com.mirechoi.backend.repository.TriplannerRepo;

@Service
public class TriplannerService {

    private final TriplannerRepo repository;

    public TriplannerService(TriplannerRepo repository) {
        this.repository = repository;
    }

    public List<TripLocation> getAllLocations() {
        return repository.findAll();
    }

    public TripLocation addLocation(TripLocation location) {
        return repository.save(location);
    }

    public void deleteLocation(Long id) {
        repository.deleteById(id);
    }
}