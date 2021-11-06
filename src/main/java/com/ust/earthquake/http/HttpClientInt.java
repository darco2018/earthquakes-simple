package com.ust.earthquake.http;

import com.ust.earthquake.domain.Earthquake;

import java.util.List;

public interface HttpClientInt {

    List<Earthquake> fetchEarthquakes();

}
