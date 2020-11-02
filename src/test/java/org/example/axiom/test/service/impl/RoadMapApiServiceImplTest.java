package org.example.axiom.test.service.impl;


import org.example.axiom.test.model.City;
import org.example.axiom.test.model.Road;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class RoadMapApiServiceImplTest {

    private static final String SAINT_PETERSBURG = "Saint-Petersburg";
    private static final String MOSCOW = "Moscow";
    private static final String TVER = "Tver";
    private final RoadMapApiServiceImpl api = new RoadMapApiServiceImpl();

    @Before
    public void setUp() {
        api.createCity(SAINT_PETERSBURG, 113, 110);
        api.createCity(MOSCOW, 150, 127);
        api.createCity(TVER, 111, 114);
        api.createRoad("Saint-Petersburg - Tver", 900, SAINT_PETERSBURG, TVER);
        api.createRoad("Tver - Saint-Petersburg", 900, TVER, SAINT_PETERSBURG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createExistsCityTest() {
        api.createCity(MOSCOW, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullCityTest() {
        api.createCity(null, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyNameCityTest() {
        api.createCity("", 1, 1);
    }

    @Test
    public void getCityTest() {
        City city = api.getCity(MOSCOW);
        assertThat(city, notNullValue());
        assertThat(city.getName(), is(equalTo(MOSCOW)));
        assertThat(city.getX(), is(equalTo(150.0)));
        assertThat(city.getY(), is(equalTo(127.0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNotExistsCityErrorTest() {
        api.getCity("Pemsa");
    }

    @Test
    public void getRoadTest() {
        List<Road> roads = api.getRoads(SAINT_PETERSBURG);
        assertThat(roads, notNullValue());
        assertThat(roads.isEmpty(), is(false));
        assertThat(roads.size(), is(equalTo(2)));
        assertThat(roads.contains(new Road("Saint-Petersburg - Tver", 900.0, SAINT_PETERSBURG, TVER)), is(true));
        assertThat(roads.contains(new Road("Tver - Saint-Petersburg", 900.0, TVER, SAINT_PETERSBURG)), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNotExistsCityRoadTest() {
        List<Road> roads = api.getRoads("Pemsa");
        assertThat(roads, notNullValue());
        assertThat(roads.isEmpty(), is(true));
    }

    @Test
    public void getNotExistsRoadTest() {
        List<Road> roads = api.getRoads(MOSCOW);
        assertThat(roads, notNullValue());
        assertThat(roads.isEmpty(), is(true));
    }

    @Test
    public void createRoadTest() {
        api.createRoad("Moscow - Saint-Petersburg", 1000, MOSCOW, SAINT_PETERSBURG);
        List<Road> roads = api.getRoads(MOSCOW);
        assertThat(roads, notNullValue());
        assertThat(roads.isEmpty(), is(false));
        assertThat(roads.size(), is(equalTo(1)));
        Road road = roads.get(0);
        assertThat(road, notNullValue());
        assertThat(road.getName(), is(equalTo("Moscow - Saint-Petersburg")));
        assertThat(road.getLength(), is(equalTo(1000.0)));
        assertThat(road.getCities(), notNullValue());
        assertThat(road.getCities().isEmpty(), is(false));
        assertThat(road.getCities().size(), is(equalTo(2)));
        assertThat(road.getCities().contains(MOSCOW), is(true));
        assertThat(road.getCities().contains(SAINT_PETERSBURG), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createExistingRoadTest() {
        api.createRoad("Saint-Petersburg - Tver", 1, SAINT_PETERSBURG, TVER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBadRoadOneCityTest() {
        api.createRoad("One city road", 1, SAINT_PETERSBURG, SAINT_PETERSBURG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBadRoadNotExistsCityTest() {
        api.createRoad("Not existing road", 1, SAINT_PETERSBURG, "Pemsa");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBadRoadNullCityTest() {
        api.createRoad("Not existing road", 1, null, "Pemsa");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBadRoadEmptyCityTest() {
        api.createRoad("Not existing road", 1, "", "Pemsa");
    }

    @Test
    public void deleteRoadTest() {
        api.deleteRoad("Saint-Petersburg - Tver");
        List<Road> roads = api.getRoads(SAINT_PETERSBURG);
        assertThat(roads, notNullValue());
        assertThat(roads.isEmpty(), is(false));
        assertThat(roads.size(), is(equalTo(1)));
        assertThat(roads.contains(new Road("Saint-Petersburg - Tver", 900, TVER, SAINT_PETERSBURG)), is(true));
        Road road = roads.get(0);
        assertThat(road, notNullValue());
        assertThat(road.getName(), is(equalTo("Tver - Saint-Petersburg")));
        assertThat(road.getLength(), is(equalTo(900.0)));
        assertThat(road.getCities(), notNullValue());
        assertThat(road.getCities().isEmpty(), is(false));
        assertThat(road.getCities().size(), is(2));
        assertThat(road.getCities().contains(TVER), is(true));
        assertThat(road.getCities().contains(SAINT_PETERSBURG), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNotExistsRoadTest() {
        api.deleteRoad("Not existing road");
    }
}