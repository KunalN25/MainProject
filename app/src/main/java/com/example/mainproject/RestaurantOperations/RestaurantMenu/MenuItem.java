package com.example.mainproject.RestaurantOperations.RestaurantMenu;

public class MenuItem {
    private String NAME;
    int PRICE;
    private boolean TYPE;   //TYPE is true for veg and false for non-veg

    public MenuItem() {
    }

    public MenuItem(String NAME, int PRICE, boolean TYPE) {
        this.NAME = NAME;
        this.PRICE = PRICE;

        this.TYPE = TYPE;
    }

    public String getName() {
        return NAME;
    }

    public int getPrice() {
        return PRICE;
    }


    public boolean isType() {
        return TYPE;
    }
}
