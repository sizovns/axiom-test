package org.example.axiom.test.model;

import java.util.Objects;

public class City {

    private final String name;
    private final double x, y;

    public City(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }


    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City city = (City) o;
        return name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "City{" +
            "name='" + name + '\'' +
            ", x=" + x +
            ", y=" + y +
            '}';
    }
}
