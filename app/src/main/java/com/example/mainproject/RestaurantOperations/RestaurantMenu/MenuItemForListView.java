package com.example.mainproject.RestaurantOperations.RestaurantMenu;

public class MenuItemForListView {

    private String name;
    private int price;
    private boolean selected=false,type;

    public MenuItemForListView(String name, int price, boolean type) {
        this.name = name;
        this.price = price;

        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isType() {
        return type;
    }
}
