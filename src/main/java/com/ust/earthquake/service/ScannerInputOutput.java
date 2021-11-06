package com.ust.earthquake.service;

import com.ust.earthquake.domain.EarthquakeDistance;
import com.ust.earthquake.domain.Location;

import java.util.List;
import java.util.Scanner;

public class ScannerInputOutput implements InputOutputInt {

    @Override
    public Location getInputLocation() {
        double latitude = -1;
        double longitude = -1;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter latitude: ");
            latitude = scanner.nextDouble();
            System.out.println("Enter longitude: ");
            longitude = scanner.nextDouble();
        } catch (Exception e) {
            System.out.println("You didn't enter a double correctly. Please, restart the program!");
        }

        return new Location(new double[]{latitude, longitude});
    }

    @Override
    public void printEarthquakesWithDistance(List<EarthquakeDistance> earthquakes) {

        StringBuilder earthquakeString = new StringBuilder();
        for (EarthquakeDistance edist : earthquakes) {
            earthquakeString.append(edist).append("\n");
        }
        System.out.println("\n" + earthquakeString);
    }
}
