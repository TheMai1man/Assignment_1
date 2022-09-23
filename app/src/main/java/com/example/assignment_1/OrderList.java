package com.example.assignment_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.example.assignment_1.OrderSchema.*;

public class OrderList {
    private List<Order> orderList = new ArrayList<>();
    private SQLiteDatabase db;

    public OrderList() {}

    public void load(Context context)
    {
        this.db = new OrderDbHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public int size() {
        return orderList.size();
    }

    public Order get(int i) {
        return orderList.get(i);
    }

    public void add(FoodItem item, int qty)
    {
        int ii = 0;
        MyCursor cursor = new MyCursor( db.query( CurrentOrder.NAME,
                null,
                CurrentOrder.Cols.ITEM_ID + " = " + item.getItemID(),
                null,
                null,
                null,
                null) );
        ii = cursor.getCount();

        if( ii == 0 )
        {
            //add to database
            ContentValues cv = new ContentValues();
            cv.put(CurrentOrder.Cols.ITEM_ID, item.getItemID());
            cv.put(CurrentOrder.Cols.QTY, qty);
            db.insert( CurrentOrder.NAME, null, cv );
            //add to list
            orderList.add(new Order(item, qty));
        }
        else
        {
            //update database
            String[] whereValue = { String.valueOf( item.getItemID() ) };
            ContentValues cv = new ContentValues();
            cv.put(CurrentOrder.Cols.ITEM_ID, item.getItemID());
            cv.put(CurrentOrder.Cols.QTY, qty + find(item.getItemID()).getQty() );
            db.update( CurrentOrder.NAME, cv, CurrentOrder.Cols.ITEM_ID + " = ?", whereValue );
            //update List
            updateQty(item.getItemID(), qty);
        }

    }

    public void edit(Order order, int qty)
    {
        //update database
        String[] whereValue = { String.valueOf( order.getId() ) };
        ContentValues cv = new ContentValues();
        cv.put(CurrentOrder.Cols.ITEM_ID, order.getId());
        cv.put(CurrentOrder.Cols.QTY, qty );
        db.update( CurrentOrder.NAME, cv, CurrentOrder.Cols.ITEM_ID + " = ?", whereValue );
        //update List
        updateQty(order.getId(), qty);
    }

    public void remove(Order order)
    {
        //remove from List
        orderList.remove(order);
        //remove from database
        String[] whereValue = { String.valueOf( order.getId() ) };
        db.delete(CurrentOrder.NAME, CurrentOrder.Cols.ITEM_ID + " = ?", whereValue);
    }

    public void updateQty(int itemID, int qty)
    {
        int ii;
        Order order = find(itemID);
        ii = qty + order.getQty();

        order.setQty(ii);
    }

    public Order find(int itemId)
    {
        int ii = 0;
        boolean found = false;
        Order o = orderList.get(0);

        do
        {
            o = orderList.get(ii);

            if(o.getId() == itemId)
            {
                found = true;
            }

            ii++;
        }while(ii < orderList.size() && !found);

        return o;
    }

    public void saveOrder(User user)
    {
        int ii = 0;
        int orderNum = newOrderNum();
        double totalPrice = 0;

        ContentValues cv = new ContentValues();

        while( ii < orderList.size() )
        {
            totalPrice += get(ii).getPrice();
            cv.put( OrderTable.Cols.ORDER_ID, orderNum );
            cv.put( OrderTable.Cols.QTY, get(ii).getQty() );
            cv.put( OrderTable.Cols.ITEM_ID, get(ii).getId() );
            db.insert(OrderTable.NAME, null, cv);
        }

        saveHistory(orderNum, totalPrice, user.getId());
    }

    public void saveHistory(int orderId, double price, int userId)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy - hh:mm aaa");
        String date = dateFormat.format(calendar.getTime());

        ContentValues cv = new ContentValues();
        cv.put( OrderHistoryTable.Cols.DATE_TIME, date );
        cv.put( OrderHistoryTable.Cols.USER_ID, userId );
        cv.put( OrderHistoryTable.Cols.COST, price );
        cv.put( OrderHistoryTable.Cols.ORDER_ID, orderId );
        db.insert(OrderHistoryTable.NAME, null, cv);
    }

    public int newOrderNum()
    {
        int num = 0;

        MyCursor cursor = new MyCursor( db.query( OrderHistoryTable.NAME,
                null,
                "MAX(" + OrderHistoryTable.Cols.ORDER_ID + ")",
                null,
                null,
                null,
                null) );
        try
        {
            if ( cursor.getCount() > 0 )
            {
                cursor.moveToFirst();
                num = cursor.getOrderID();
            }
        }
        finally
        {
            cursor.close();
        }

        return num + 1;
    }
}
