package com.example.mainproject.RestaurantOperations.RestaurantMenu

class MenuItem {
    var NAME: String = ""
    var PRICE: Int = 0
        internal set
    var TYPE: Boolean = false   //TYPE is true for veg and false for non-veg

    constructor() {}

    constructor(NAME: String, PRICE: Int, TYPE: Boolean) {
        this.NAME = NAME
        this.PRICE = PRICE

        this.TYPE = TYPE
    }
}
