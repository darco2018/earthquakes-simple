package com.ust.earthquake.service;

import com.ust.earthquake.domain.Earthquake;
import com.ust.earthquake.domain.EarthquakeDistance;
import com.ust.earthquake.domain.Location;

import java.util.*;

/*https://gps-coordinates.org/coordinate-converter.php
 * https://gps-coordinates.org/distance-between-cities.php
 * https://www.geodatasource.com/developers/java
 * https://www.latlong.net/*/
public class Utils {

    public static List<EarthquakeDistance> sortEarthquakesAsc(int limit, List<Earthquake> earthquakes,
                                                              Location target){
        limit = Math.min(limit, earthquakes.size());

        List<EarthquakeDistance> earthquakeDistance = new ArrayList<>();
        for (Earthquake e : earthquakes) {
            Location location = e.getLocation();
            int distance = getDistance(location, target);
            assert (distance >= 0);

            earthquakeDistance.add(new EarthquakeDistance(e, distance));
        }

        //printGivenEarthquakes("\nUnsorted earthquakes:", earthquakeDistance, 0,1,2,1500 );
        Collections.sort(earthquakeDistance); // uses Comparator in EarthquakeDistance
        //printGivenEarthquakes("\nSorted earthquakes:", earthquakeDistance, 0,1500,5000 );

        return earthquakeDistance.subList(0,limit);
    }

    /*This code is contributed by Prasad Kshirsagar
    https://www.geeksforgeeks.org/program-distance-two-points-earth */
    public static int getDistance(Location loc1, Location loc2){
        //TODO implement distance between 2 locations
        // We would like each distance to be rounded to full kilometers
        double lat1 = loc1.getLatitude();
        double lon1 = loc1.getLongitude();
        double lat2 = loc2.getLatitude();
        double lon2 = loc2.getLongitude();

            // The math module contains a function
            // named toRadians which converts from
            // degrees to radians.
            lon1 = Math.toRadians(lon1);
            lon2 = Math.toRadians(lon2);
            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);

            // Haversine formula
            double dlon = lon2 - lon1;
            double dlat = lat2 - lat1;
            double a = Math.pow(Math.sin(dlat / 2), 2)
                    + Math.cos(lat1) * Math.cos(lat2)
                    * Math.pow(Math.sin(dlon / 2),2);

            double c = 2 * Math.asin(Math.sqrt(a));

            // Radius of earth in kilometers. Use 3956
            // for miles
            double r = 6371;

            // calculate the result
            return (int) (c * r);

    }

    public static int getDistance(double lat1, double lon1, double lat2, double lon2, String unit) { //"K" for km
            if ((lat1 == lat2) && (lon1 == lon2)) {
                return 0;
            }
            else {
                double theta = lon1 - lon2;
                double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
                dist = Math.acos(dist);
                dist = Math.toDegrees(dist);
                dist = dist * 60 * 1.1515;
                if (unit.equals("K")) {
                    dist = dist * 1.609344;
                } else if (unit.equals("N")) {
                    dist = dist * 0.8684;
                }
                return (int) dist;
            }

    }

    public static List<Earthquake> removeDuplicateEarthquakes(List<Earthquake> earthquakes) {
        Set<Earthquake> noDuplicates = new HashSet<>(earthquakes);
        //System.out.println(">>>>>>>>>> noDuplicates: " + noDuplicates.size());
        //SortedSet<Earthquake> noDuplicates2 = new TreeSet<>(earthquakes);

        return new ArrayList<>(noDuplicates);
    }

    public static void printEarthquakes(List<Earthquake> earthquakes) {
        StringBuilder earthquakeString = new StringBuilder();
        for (Earthquake earthquake : earthquakes) {
            earthquakeString.append(earthquake.toString()).append("\n");
        }
        System.out.println(earthquakeString);
    }

    public static void printGivenEarthquakes(String msg, List<EarthquakeDistance> eDist, int... indexes){
        System.out.println(msg);
        for(int i : indexes){
            System.out.println(i + ". " + eDist.get(i));
        }
    }


}
