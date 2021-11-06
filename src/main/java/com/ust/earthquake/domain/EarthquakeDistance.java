package com.ust.earthquake.domain;

public class EarthquakeDistance implements Comparable<EarthquakeDistance> {

    private final Earthquake earthquake;
    private final int distance;

    public EarthquakeDistance(Earthquake earthquake, int distance) {
        this.earthquake = earthquake;
        this.distance = distance;
    }

    public Earthquake getEarthquake() {
        return earthquake;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return earthquake.getProperties().getTitle() + " || " + getDistance();
    }

    @Override
    public int compareTo(EarthquakeDistance o) {
        return this.distance - o.distance;
    }
}
