package com.ust.earthquake.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.earthquake.domain.Data;
import com.ust.earthquake.domain.Earthquake;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HttpURLConnectionClient implements HttpClientInt {

    private static final Logger logger = LogManager.getLogger(HttpURLConnectionClient.class);
    private final String REQUEST_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";

    @Override
    public List<Earthquake> fetchEarthquakes() {

        Data data = null;
        String json = "";

        try {
            URL url = new URL(REQUEST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream response = connection.getInputStream();
            json = new String(response.readAllBytes(), StandardCharsets.UTF_8);
            connection.disconnect();

            //System.out.println(json.substring(0, 3000));

            try {

                ObjectMapper mapper = new ObjectMapper();
                data = mapper.readValue(json, Data.class);
                //System.out.println(data);

            } catch (Exception e) {
                logger.error("Failed to convert json string into Java objects.");
                e.printStackTrace();
            }

        } catch (Exception e) {
            logger.error("Failed to fetch data from " + REQUEST_URL);
            e.getStackTrace();
        }



        return Objects.isNull(data) ?
                new ArrayList<>() : Arrays.asList(data.getEarthquakes());
    }
}
