package com.example.mywebquizengine.Model.Geo;

import com.example.mywebquizengine.Model.User;

import javax.persistence.*;
import java.util.Calendar;

@Entity(name = "GEOLOCATIONS")
public class Geolocation {

    @Id
    private String id;

    //@JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId
    @JoinColumn(name = "username")
    private User user;

    private Double lat;
    private Double lng;

    private Calendar time;

    public Geolocation() {}

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar calendar) {
        this.time = calendar;
    }

    @Override
    public String toString() {
        return "Geolocation{" +
                "id='" + id + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
