package com.mirechoi.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mirechoi.backend.entity.TripLocation;
import com.mirechoi.backend.service.TriplannerService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/locations")
public class TriplannerController {

    private final TriplannerService service;

    public TriplannerController(TriplannerService service){
        this.service = service;
    }

    @GetMapping
    public List<TripLocation> getLocations() {
        return service.getAllLocations();
    }
    
    @PostMapping
    public TripLocation addLocation(@RequestBody TripLocation location){
        if(location.getAddress() == null) location.setAddress("");
        if(location.getAirport() == null) location.setAirport("");
        if(location.getAccommodation() == null) location.setAccommodation("");
        return service.addLocation(location);
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Long id){
        service.deleteLocation(id);
    }
}
