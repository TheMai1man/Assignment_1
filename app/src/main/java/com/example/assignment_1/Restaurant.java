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
        this.menu = getMenu();
        this.resID = resID;
        this.name = name;
        this.logo = logo;
    }

    public List<FoodItem> getMenu()
    {
        //load FoodItems from database into menu
        return this.menu;
    }
    public String getName()
    {
        return this.name;
    }
    public int getResID()
    {
        return this.resID;
    }
    public int getLogo()
    {
        return this.logo;
    }
}
