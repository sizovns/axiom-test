package org.example.axiom.test.service.impl;

import org.example.axiom.test.model.City;
import org.example.axiom.test.model.Road;
import org.example.axiom.test.service.RoadMapApiService;
import org.example.axiom.test.service.SyncPrimitive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoadMapApiServiceImpl implements RoadMapApiService {

    private final Map<String, City> cityMap;
    private final Map<String, Road> roadMap;
    private final SyncPrimitive syncPrimitive;

    public RoadMapApiServiceImpl() {
        this.cityMap = new HashMap<>();
        this.roadMap = new HashMap<>();
        this.syncPrimitive = new SemaphoreSyncPrimitive();
    }

    @Override
    public void createCity(String name, double x, double y) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Cannot create city with name: " + name);
        }
        syncPrimitive.acquireLock();
        try {
            if (cityMap.containsKey(name)) {
                throw new IllegalArgumentException("City with name: " + name + " already exists");
            }
            cityMap.put(name, new City(name, x, y));
        } finally {
            syncPrimitive.releaseLock();
        }
    }

    @Override
    public void createRoad(String name, double length, String fromCity, String toCity) {
        if (fromCity == null || fromCity.isEmpty() || toCity == null || toCity.isEmpty() || fromCity.equals(toCity)) {
            throw new IllegalArgumentException(
                "Cannot create road from city: " + fromCity + " to city: " + toCity);
        }
        syncPrimitive.acquireLock();
        try {
            if (!cityMap.containsKey(fromCity) || !cityMap.containsKey(toCity)) {
                throw new IllegalArgumentException(
                    "No city with name " + fromCity + " or " + toCity + " check cities before add road");
            }
            if (roadMap.containsKey(name)) {
                throw new IllegalArgumentException("Road with name: " + name + " already exists");
            }
            roadMap.put(name, new Road(name, length, fromCity, toCity));
        } finally {
            syncPrimitive.releaseLock();
        }
    }

    @Override
    public void deleteRoad(String name) {
        syncPrimitive.acquireLock();
        try {
            if (!roadMap.containsKey(name)) {
                throw new IllegalArgumentException("No road with name: " + name);
            }
            roadMap.remove(name);
        } finally {
            syncPrimitive.releaseLock();
        }
    }

    @Override
    public City getCity(String name) {
        City city;
        syncPrimitive.acquireLock();
        try {
            if (!cityMap.containsKey(name)) {
                throw new IllegalArgumentException("City with name: " + name + " not exists");
            }
            city = cityMap.get(name);
        } finally {
            syncPrimitive.releaseLock();
        }
        return city;
    }

    @Override
    public List<Road> getRoads(String cityName) {
        List<Road> roads;
        syncPrimitive.acquireLock();
        try {
            if (!cityMap.containsKey(cityName)) {
                throw new IllegalArgumentException("City with name: " + cityName + " not exists");
            }
            roads = roadMap.values().stream()
                .filter(road -> road.getCities().stream().anyMatch(city -> city.equals(cityName)))
                .collect(Collectors.toList());
        } finally {
            syncPrimitive.releaseLock();
        }
        return roads;
    }
}
