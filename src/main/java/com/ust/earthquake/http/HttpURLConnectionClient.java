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

/*By default, the class java.net.HttpURLConnection from the Java SDK is used in RestTemplate. However,
the Spring Framework makes it possible to easily switch to another HTTP client API.

Most of us surely have experience with HttpURLConnection or another HTTP client API. When using it
we noticed that for each request the same boilerplate code is generated again and again:

Creating a URL object and opening the connection
Configuring the HTTP request
Executing the HTTP request
Interpretation of the HTTP response
Converting the HTTP response into a Java object
Exception handling
When using RestTemplate all these things happen in the background and the developer doesn’t have to
bother with it.

Starting with Spring 5, the non-blocking and reactive WebClient offers a modern alternative to RestTemplate. WebClient
offers support for both synchronous and asynchronous HTTP requests and streaming scenarios. Therefore, RestTemplate
will be marked as deprecated in a future version of the Spring Framework and will not contain
any new functionalities.*/
public class HttpURLConnectionClient implements HttpClientInt {

    private static final Logger logger = LogManager.getLogger(HttpURLConnectionClient.class);
    private final String REQUEST_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";

    @Override
    public List<Earthquake> fetchEarthquakes() {

        Data data = null;
        String json = "";

        InputStream response;
        try {
            URL url = new URL(REQUEST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int responsecode = connection.getResponseCode();
            if (responsecode == 200) {
                response = connection.getInputStream();
            } else {
                throw new RuntimeException("Http response code:  + responsecode");
            }

            json = new String(response.readAllBytes(), StandardCharsets.UTF_8);
            connection.disconnect();

            try {
                ObjectMapper mapper = new ObjectMapper();
                data = mapper.readValue(json, Data.class);
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
