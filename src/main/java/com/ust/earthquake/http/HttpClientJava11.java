package com.ust.earthquake.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.earthquake.App;
import com.ust.earthquake.domain.Data;
import com.ust.earthquake.domain.Earthquake;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HttpClientJava11 implements HttpClientInt {

    private static final Logger logger = LogManager.getLogger(App.class);
    private final String REQUEST_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";

    @Override
    public List<Earthquake> fetchEarthquakes() {

        Data data = null;
        String json = "";

        HttpClient clnt = HttpClient.newHttpClient(); // Returns a new HttpClient with default settings.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(REQUEST_URL)).GET()
                .build();
        /* abstract <T> CompletableFuture<HttpResponse<T>>	sendAsync(HttpRequest request,
                                                              HttpResponse.BodyHandler<T> responseBodyHandler)
        Sends the given request asynchronously using this client with the given response body handler.
        The returned completable future, if completed successfully, completes with an HttpResponse<T>
        that contains the response status, headers, and body ( as handled by given response body handler ).
        The returned completable future completes exceptionally with:
        IOException - if an I/O error occurs when sending or receiving
        */

        /*     json = clnt.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body) // response.body()
                  //.thenAccept(System.out::println) // System.out.println(json)
                .join();
        */


        /* abstract <T> HttpResponse<T>	send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler)
        Sends the given request using this client, blocking if necessary to get the response.
        The returned HttpResponse<T> contains the response status, headers, and body ( as handled by given response body handler)
        Throws: IOException - if an I/O error occurs when sending or receiving,
         InterruptedException - if the operation is interrupted */

        HttpResponse<String> response = null;
        try {
            response = clnt.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                json = response.body();
            } else {
                throw new RuntimeException("Http response code:  + responsecode");
            }

            try {
                ObjectMapper mapper = new ObjectMapper();
                data = mapper.readValue(json, Data.class);
            } catch (Exception e) {
                logger.error("Failed to convert json string into Java objects.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("Failed to fetch data from " + REQUEST_URL);
            e.printStackTrace();
        }

        return Objects.isNull(data) ?
                new ArrayList<>() : Arrays.asList(data.getEarthquakes());
    }
}
