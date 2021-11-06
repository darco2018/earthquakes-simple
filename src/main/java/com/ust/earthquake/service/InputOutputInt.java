package com.ust.earthquake.service;

import com.ust.earthquake.domain.EarthquakeDistance;
import com.ust.earthquake.domain.Location;

import java.util.List;

public interface InputOutputInt {

    Location getInputLocation();

    void printEarthquakesWithDistance(List<EarthquakeDistance> earthquakes);
}
