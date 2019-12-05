package com.example.mainproject.RestaurantOperations.Reviews;

public class ReviewData {
    private String customerName,photo_url,reviewText;
    double rating;

    public ReviewData() {
    }

    public ReviewData(String customerName, String photo_url, String reviewText, double rating) {
        this.customerName = customerName;
        this.photo_url = photo_url;
        this.reviewText = reviewText;
        this.rating=rating;
    }



    public String getCustomerName() {
        return customerName;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public String getReviewText() {
        return reviewText;
    }

    public double getRating() {
        return rating;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
