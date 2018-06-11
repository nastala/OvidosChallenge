package com.example.nastala.ovidoschallenge.Classes;

import java.util.ArrayList;

public class UserAddress {
    private String street, suite, city, zipCode;
    private ArrayList<String> geo;

    public UserAddress(String street, String suite, String city, String zipCode, String lat, String lng){
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipCode = zipCode;
        geo = new ArrayList<>();
        geo.add(lat);
        geo.add(lng);
    }

    public String getStreet() {
        return street;
    }

    public String getSuite() {
        return suite;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public ArrayList<String> getGeo() {
        return geo;
    }
}
