package com.ust.earthquake.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Earthquake {

    private final Location location;
    private final Properties properties;

    @JsonCreator
    public Earthquake(@JsonProperty("geometry") Location location,
                      @JsonProperty("properties") Properties properties) {
        this.location = location;
        this.properties = properties;
    }

    public Location getLocation() {
        return location;
    }

    public Properties getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return location + ", " + properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earthquake that = (Earthquake) o;
        return Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}
