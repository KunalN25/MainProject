package com.example.mainproject.RestaurantOperations.RestaurantMenu;

public class MenuItem {
    private String name,price;

    public MenuItem(String name, String price) {
        this.name = name;
        this.price = price;

    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }


}
