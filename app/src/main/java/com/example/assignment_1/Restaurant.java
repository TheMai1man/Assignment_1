package com.example.assignment_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.assignment_1.OrderSchema.*;
import java.util.List;

public class Restaurant
{
    private List<FoodItem> menu;
    private final int resID;
    private final String name;
    private final int logo;
    private SQLiteDatabase db;

    public Restaurant( int resID, String name, int logo )
    {
        this.resID = resID;
        this.name = name;
        this.logo = logo;
    }

    public void loadMenu(Context context)
    {
        //load FoodItems from database into menu
    }

    public List<FoodItem> getMenu()
    {
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
