package com.example.mainproject.RestaurantOperations.RestaurantValuesClasses;

import org.json.JSONArray;

import java.io.Serializable;

//This class has the type od data you want to put in the recycler view
public class RestaurantJSONItems implements Serializable {
    private String name,photos_url,cuisines,phone_numbers,ratings;
    private String reviews;
    private int average_cost_for_two;
    private RestaurantLocation location;

    public RestaurantJSONItems(String name, String photos_url, int average_cost_for_two
            , RestaurantLocation location, String cuisines, String phone_numbers, String ratings, String reviews) {
        this.name = name;
        this.photos_url = photos_url;
        this.average_cost_for_two=average_cost_for_two;
        this.location=location;
        this.cuisines=cuisines;
        this.phone_numbers = phone_numbers;
        this.ratings = ratings;
        this.reviews = reviews;
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


    public int getAverage_cost_for_two() {
        return average_cost_for_two;
    }

    public String getCuisines() {
        return cuisines;
    }

    public String getPhone_numbers() {
        return phone_numbers;
    }

    public String getRatings() {
        return ratings;
    }

    public String getReviews() {
        return reviews;
    }
}

