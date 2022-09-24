package com.example.assignment_1;

public class Order
{
    private FoodItem item;
    private int qty;

    public Order(FoodItem fi, int q)
    {
        this.item = fi;
        this.qty = q;
    }

    public String getName()
    {
        return this.item.getName();
    }
    public double getPrice()
    {
        return this.item.getPrice();
    }
    public int getQty()
    {
        return this.qty;
    }
    public int getId()
    {
        return this.item.getItemID();
    }
    public void setQty(int i)
    {
        this.qty = i;
    }
}
