package com.example.mainproject.RestaurantOperations.RestaurantValuesClasses;

import java.io.Serializable;

//This class has the type od data you want to put in the recycler view
public class RestaurantJSONItems implements Serializable {
    private String name,photos_url,res_id,cuisines;
    private int average_cost_for_two;
    private RestaurantLocation location;

    public RestaurantJSONItems(String name, String photos_url, String res_id, int average_cost_for_two
            , RestaurantLocation location, String cuisines) {
        this.name = name;
        this.photos_url = photos_url;
        this.res_id = res_id;
        this.average_cost_for_two=average_cost_for_two;
        this.location=location;
        this.cuisines=cuisines;
    }

    public RestaurantLocation getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPhotos_url() {
        return photos_url;
    }

    public String getRes_id() {
        return res_id;
    }

    public int getAverage_cost_for_two() {
        return average_cost_for_two;
    }

    public String getCuisines() {
        return cuisines;
    }
}

