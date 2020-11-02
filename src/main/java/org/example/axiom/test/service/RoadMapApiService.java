package org.example.axiom.test.service;

import org.example.axiom.test.model.City;
import org.example.axiom.test.model.Road;

import java.util.List;


public interface RoadMapApiService {
    /**
     * Create city, if city exists will throw {@link IllegalArgumentException}
     *
     * @param name - String name of city
     * @param x    - double x position of city
     * @param y    - double y position of city
     */
    void createCity(String name, double x, double y);

    /**
     * Create road, from one city to another, if one of the city not exists or
     * if {@code fromCity} and {@code toCity} names are equal or road with this name are exists
     * throw {@link IllegalArgumentException}
     *
     * @param name     - String name of road
     * @param length   - double length of road
     * @param fromCity - String road from city name
     * @param toCity   - String road to city name
     */
    void createRoad(String name, double length, String fromCity, String toCity);

    /**
     * Delete road by name, if road not exists throw {@link IllegalArgumentException}
     *
     * @param name - String road name
     */
    void deleteRoad(String name);

    /**
     * Get city by name, if city not exists throw {@link IllegalArgumentException}
     *
     * @param name - Sting name of city
     * @return - {@link City} founded city
     */
    City getCity(String name);

    /**
     * Get roads by city, if city not exists throw {@link IllegalArgumentException}
     *
     * @param cityName - String name of the city
     * @return - List of {@link Road}
     */
    List<Road> getRoads(String cityName);
}
