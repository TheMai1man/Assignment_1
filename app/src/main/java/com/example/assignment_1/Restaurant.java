package com.example.assignment_1;

import java.util.List;

public class Restaurant
{
    private List<FoodItem> menu;
    private final int resID;
    private final String name;
    private final int logo;

    public Restaurant( int resID, String name, int logo )
    {
        this.resID = resID;
        this.name = name;
        this.logo = logo;
    }

    public static void getMenu()
    {
        //load FoodItems from database into menu
    }
}
