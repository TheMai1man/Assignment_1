package com.example.assignment_1;

import java.util.ArrayList;
import java.util.List;

public class Restaurant
{
    private List<FoodItem> menu = new ArrayList<>();
    private final int resID;
    private final String name;
    private final int logo;

    public Restaurant( int resID, String name, int logo )
    {
        this.resID = resID;
        this.name = name;
        this.logo = logo;
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

    public void add(FoodItem fi)
    {
        menu.add(fi);
    }
    public FoodItem get(int i)
    {
        return menu.get(i);
    }
    public int size()
    {
        return menu.size();
    }
}
