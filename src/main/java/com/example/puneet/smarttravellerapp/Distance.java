package com.example.puneet.smarttravellerapp;

import java.io.Serializable;
import java.util.Objects;

public class Distance implements Serializable
{
    private String place;
    private String latitude;
    private String longitude;
    private String location;
    private String avg_est_time;

    public Distance(String place, String latitude, String longitude, String location, String avg_est_time)
    {
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.avg_est_time = avg_est_time;
    }

    public String getPlace() {
        return place;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLocation() {
        return location;
    }

    public String getAvg_est_time() {
        return avg_est_time;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Distance)) return false;
        Distance distance = (Distance) o;
        return Objects.equals(getPlace(), distance.getPlace()) &&
                Objects.equals(getLatitude(), distance.getLatitude()) &&
                Objects.equals(getLongitude(), distance.getLongitude()) &&
                Objects.equals(getLocation(), distance.getLocation()) &&
                Objects.equals(getAvg_est_time(), distance.getAvg_est_time());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getPlace(), getLatitude(), getLongitude(), getLocation(), getAvg_est_time());
    }

    @Override
    public String toString()
    {
        return place+":"+latitude+","+longitude;
    }
}
