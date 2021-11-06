package com.ust.earthquake;

import com.ust.earthquake.domain.Earthquake;
import com.ust.earthquake.domain.EarthquakeDistance;
import com.ust.earthquake.domain.Location;
import com.ust.earthquake.http.HttpClientInt;
import com.ust.earthquake.http.HttpURLConnectionClient;
import com.ust.earthquake.service.InputOutputInt;
import com.ust.earthquake.service.ScannerInputOutput;
import com.ust.earthquake.service.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);


    public static void main(String[] args) {
        logger.info("Collecting coordinates for the central location...");
        InputOutputInt inputOutput = new ScannerInputOutput();
        //Location location = new Location(new double[]{50.0592844, 19.9367797}); // cracow
        Location location = inputOutput.getInputLocation();
        logger.info("These coordinates have been entered: " + location);

        ///////////////////////////
        
        logger.info("Creating HttpClient...");
        // assign a different client if you want to
        HttpClientInt client = new HttpURLConnectionClient();
        logger.info("Fetching all earthquakes. Can take some time...");
        List<Earthquake> earthquakes = client.fetchEarthquakes();
        logger.info("Fetching complete");

        ///////////////////////////////

        if (earthquakes.size() > 0) {
            // Utils.printEarthquakes(earthquakes);
            logger.info("Fetching successful. " + earthquakes.size() + " earthquakes in the last 30 days.");
        } else {
            logger.info("Fetching unsuccessful. Earthquakes fetched: " + 0);
            System.exit(0);
        }

        logger.info("Removing duplicates...");
        earthquakes = Utils.removeDuplicateEarthquakes(earthquakes);
        logger.info(earthquakes.size() + " after duplicate removal.");

        logger.info("Finding nearest earthquakes...");
        List<EarthquakeDistance> nearest = Utils.sortEarthquakesAsc(10, earthquakes, location);

        logger.info("Displaying nearest earthquakes with distance from " + location + "...");
        inputOutput.printEarthquakesWithDistance(nearest);
    }

}
