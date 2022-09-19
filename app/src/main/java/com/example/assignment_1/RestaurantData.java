package com.example.assignment_1;

import java.util.List;

public class RestaurantData
{
    private List<Restaurant> restaurantList;

    private static RestaurantData instance = null;

    public static RestaurantData get()
    {
        if(instance == null)
        {
            instance = new RestaurantData();
        }
        return instance;
    }

    protected RestaurantData()
    {
        //load from database
    }

    public Restaurant get(int i)
    {
        return restaurantList.get(i);
    }

    public int size()
    {
        return restaurantList.size();
    }
}
