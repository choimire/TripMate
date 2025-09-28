package com.mirechoi.backend.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;





@Service
public class DistanceMatrixService {

    @Value("${google.maps.api-key}")
    private String apiKey;

    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    // origins/destinations: 배열 -> time (seconds) matrix 반환
    public int[][] getDistanceMatrix(String[] places){
        try{
            String origins = String.join("|", places);
            String destinations = origins;
            String url = String.format(
                "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&mode=driving&key=%s",
                encode(origins), encode(destinations), apiKey
            );
            String body = rest.getForObject(url, String.class);
            JsonNode root = mapper.readTree(body);
            JsonNode rows = root.path("rows");
            int n = places.length;
            int[][] matrix = new int[n][n];
            for(int i=0;i<n;i++){
                JsonNode elements = rows.get(i).path("elements");
                for(int j=0;j<n;j++){
                    JsonNode el = elements.get(j);
                    matrix[i][j] = el.path("duration").path("value").asInt(99999999);
                }
            }
            return matrix;
        }catch(Exception e){
            e.printStackTrace();
            return new int[0][0];
        }
    }

    // 간단한 geocode: place -> lat,lng,address
    public Map<String,Object> geocodePlace(String place){
        try{
            String url = String.format(
                "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s",
                encode(place), apiKey
            );
            String body = rest.getForObject(url, String.class);
            JsonNode root = mapper.readTree(body);
            JsonNode res = root.path("results");
            if(res.isArray() && res.size()>0){
                JsonNode first = res.get(0);
                double lat = first.path("geometry").path("location").path("lat").asDouble();
                double lng = first.path("geometry").path("location").path("lng").asDouble();
                String formatted = first.path("formatted_address").asText();
                Map<String,Object> out = new HashMap<>();
                out.put("lat", lat);
                out.put("lng", lng);
                out.put("address", formatted);
                return out;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // Nearest Neighbor TSP (시간 기준 matrix)
    public int[] solveTSPNearestNeighbor(int[][] matrix){
        int n = matrix.length;
        boolean[] used = new boolean[n];
        int[] order = new int[n];
        int cur = 0;
        for(int i=0;i<n;i++) order[i] = -1;
        order[0] = 0; used[0]=true;
        for(int step=1; step<n; step++){
            int next = -1; int best = Integer.MAX_VALUE;
            for(int j=0;j<n;j++){
                if(used[j]) continue;
                if(matrix[cur][j] < best){
                    best = matrix[cur][j];
                    next = j;
                }
            }
            if(next==-1)
                for(int j=0;j<n;j++) if(!used[j]){ next=j; break; }
            order[step]=next;
            used[next]=true;
            cur = next;
        }
        return order;
    }

    private String encode(String s){
        return s.replace(" ", "+");
    }

    public Map<String,Object> geocodePlaceToMap(String place){
    Map<String,Object> map = new HashMap<>();
    map.put("place", place);
    Map<String,Object> geo = geocodePlace(place);
    if(geo != null){
        map.put("lat", geo.get("lat"));
        map.put("lng", geo.get("lng"));
        map.put("address", geo.get("address"));
    }
    map.put("travelSecFromPrev", 0); // 초기값
    return map;
    }
}