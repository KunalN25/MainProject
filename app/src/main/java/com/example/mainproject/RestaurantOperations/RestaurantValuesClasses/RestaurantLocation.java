package com.example.mainproject.RestaurantOperations.RestaurantValuesClasses;

import java.io.Serializable;

public class RestaurantLocation implements Serializable {
    private String city,address,locality_verbose;



    public RestaurantLocation(String city, String address, String locality_verbose) {
        this.city = city;
        this.address = address;
        this.locality_verbose = locality_verbose;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getLocality_verbose() {
        return locality_verbose;
    }
}