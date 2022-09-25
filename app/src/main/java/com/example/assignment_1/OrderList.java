package com.example.assignment_1;

import android.content.ContentValues;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.example.assignment_1.OrderSchema.*;

public class OrderList
{
    private List<Order> orderList = new ArrayList<>();

    public OrderList() {}

    public int size() {
        return orderList.size();
    }

    public Order get(int i) {
        return orderList.get(i);
    }

    public void add(Order order)
    {
        if( (orderList.size() == 0) || find(order.getId()) == null )
        {
            //add to list
            orderList.add(order);
        }
        else
        {
            //update List
            addQty(order, order.getQty());
        }

    }

    public void editQty(Order order, int qty)
    {
        //update List
        Order o = find(order.getId());
        o.setQty(qty);
    }

    public void remove(Order order)
    {
        //remove from List
        orderList.remove(order);
    }

    public void addQty(Order order, int qty)
    {
        Order o = find(order.getId());
        o.setQty(qty + o.getQty());
    }

    public Order find(int itemId)
    {
        int ii = 0;
        boolean found = false;
        Order o;

        do
        {
            o = orderList.get(ii);

            if(o.getId() == itemId)
            {
                found = true;
            }

            ii++;
        }while(ii < orderList.size() && !found);

        if(!found)
        {
            o = null;
        }

        return o;
    }

}
