package com.example.assignment_1;

import java.util.List;

public class OrderList
{
    private List<Order> orderList;

    public OrderList() {}

    public int size()
    {
        return orderList.size();
    }
    public Order get(int i)
    {
        return orderList.get(i);
    }

    public void add(FoodItem item, int qty)
    {
        orderList.add( new Order(item, qty) );
    }
}
