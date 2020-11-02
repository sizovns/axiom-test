package org.example.axiom.test.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Road {

    private final String name;
    private final double length;
    private final Set<String> cities;

    public Road(String name, double length, String fromCity, String toCity) {
        this.name = name;
        this.length = length;
        cities = new HashSet<>();
        cities.add(fromCity);
        cities.add(toCity);
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }

    public Set<String> getCities() {
        return cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Road road = (Road) o;
        return name.equals(road.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Road{" +
            "name='" + name + '\'' +
            ", length=" + length +
            '}';
    }
}
