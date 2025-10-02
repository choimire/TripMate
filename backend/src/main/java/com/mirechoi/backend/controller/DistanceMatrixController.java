package com.mirechoi.backend.controller;

import org.springframework.web.bind.annotation.*;
import com.mirechoi.backend.service.DistanceMatrixService;

import java.util.*;

@RestController
@RequestMapping("/api")
public class DistanceMatrixController {

    private final DistanceMatrixService service;

    public DistanceMatrixController(DistanceMatrixService service){
        this.service = service;
    }

    @GetMapping("/optimize")
    public List<Map<String,Object>> optimizeRoute(
            @RequestParam String location,
            @RequestParam(defaultValue="1") int dayCount,
            @RequestParam(required=false) String accommodation,
            @RequestParam(required=false) String airport){

        // 여행지 파싱
        String[] locArray = Arrays.stream(location.split(","))
                                  .map(String::trim)
                                  .filter(s -> !s.isEmpty())
                                  .toArray(String[]::new);
        if(locArray.length == 0) return Collections.emptyList();

        List<String> travelPlaces = new ArrayList<>(Arrays.asList(locArray));

        // 거리 matrix
        int[][] matrix = service.getDistanceMatrix(travelPlaces.toArray(new String[0]));
        int[] order = service.solveTSPNearestNeighbor(matrix);

        // TSP 순서대로 정렬
        List<String> orderedTravel = new ArrayList<>();
        for(int idx : order) orderedTravel.add(travelPlaces.get(idx));

        // 하루 일정 분배
        int perDay = (int)Math.ceil((double)orderedTravel.size() / dayCount);
        List<Map<String,Object>> schedule = new ArrayList<>();

        for(int i=0; i<dayCount; i++){
            int start = i * perDay;
            int end = Math.min(start + perDay, orderedTravel.size());
            List<Map<String,Object>> placesOfDay = new ArrayList<>();

            // 첫째 날: 공항 -> 숙소 -> 여행지
            if(i == 0){
                if(airport != null && !airport.isEmpty())
                    placesOfDay.add(service.geocodePlaceToMap(airport));
                if(accommodation != null && !accommodation.isEmpty())
                    placesOfDay.add(service.geocodePlaceToMap(accommodation));
            } else {
                // 둘째 날 이후: 첫 일정은 숙소
                if(accommodation != null && !accommodation.isEmpty())
                    placesOfDay.add(service.geocodePlaceToMap(accommodation));
            }

            // 해당 날 여행지 추가
            for(int j=start; j<end; j++){
                placesOfDay.add(service.geocodePlaceToMap(orderedTravel.get(j)));
            }

            // 마지막 날: 공항 마지막
            if(i == dayCount - 1 && airport != null && !airport.isEmpty())
                placesOfDay.add(service.geocodePlaceToMap(airport));

            Map<String,Object> dayMap = new HashMap<>();
            dayMap.put("day", i+1);
            dayMap.put("places", placesOfDay);
            schedule.add(dayMap);
        }

        return schedule;
    }
    
}
