package com.example.mainproject.RestaurantOperations.RestaurantMenu;

public class MenuItemForListView {

    private String name,price;
    private boolean selected=false;

    public MenuItemForListView(String name, String price) {
        this.name = name;
        this.price = price;

    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
