package com.mirechoi.backend.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mirechoi.backend.entity.TripLocation;
import com.mirechoi.backend.repository.TriplannerRepo;

@Service
public class TriplannerService {

    private final TriplannerRepo repository;
    private final DistanceMatrixService distanceMatrixService;

    public TriplannerService(TriplannerRepo repository, DistanceMatrixService service) {
        this.repository = repository;
        this.distanceMatrixService = service;
    }

    public List<TripLocation> getAllLocations() {
        return repository.findAll();
    }

    public TripLocation addLocation(TripLocation location){
        Map<String, Object> geo = distanceMatrixService.geocodePlace(location.getName());

        if(geo != null){
            location.setAddress((String) geo.get("address"));
        } else {
            location.setAddress("");
        }

        if(location.getAirport() == null) location.setAirport("");
        if(location.getAccommodation() == null) location.setAccommodation("");

        return repository.save(location);
    }
    public void deleteLocation(Long id) {
        repository.deleteById(id);
    }
}