package com.example.assignment_1;

public class FoodItem
{
    private final int resID;
    private final int itemID;
    private final String name;
    private final String description;
    private final double price;
    private final int photo;

    public FoodItem( int resID, int itemID, String name, String description, double price, int photo )
    {
        this.resID = resID;
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.photo = photo;
    }

    public int getResID()
    {
        return resID;
    }
    public int getItemID()
    {
        return itemID;
    }
    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }
    public double getPrice()
    {
        return price;
    }
    public int getPhoto()
    {
        return photo;
    }
}
