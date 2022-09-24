package com.example.assignment_1;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.assignment_1.OrderSchema.*;

public class MyCursor extends CursorWrapper
{
    public MyCursor(Cursor cursor)
    {
        super(cursor);
    }

    public FoodItem getMenuItem()
    {
        int resID = getInt( getColumnIndex( FoodItemTable.Cols.RES_ID ) );
        int itemID = getInt( getColumnIndex( FoodItemTable.Cols.ITEM_ID ) );
        String name = getString( getColumnIndex( FoodItemTable.Cols.NAME ) );
        String description = getString( getColumnIndex( FoodItemTable.Cols.DESCRIPTION ) );
        double price = getDouble( getColumnIndex( FoodItemTable.Cols.PRICE ) );
        int photo = getInt( getColumnIndex( FoodItemTable.Cols.PHOTO ) );

        return new FoodItem(resID, itemID, name, description, price, photo);
    }

    public Restaurant getRestaurant()
    {
        int resID = getInt( getColumnIndex( RestaurantTable.Cols.RES_ID ) );
        String name = getString( getColumnIndex( RestaurantTable.Cols.NAME ) );
        int logo = getInt( getColumnIndex( RestaurantTable.Cols.LOGO ) );

        return new Restaurant(resID, name, logo);
    }

    public int getOrderID()
    {
        return getInt( getColumnIndex( OrderHistoryTable.Cols.ORDER_ID ) );
    }

    public User getUser()
    {
        int id = getInt( getColumnIndex( UserTable.Cols.USER_ID ) );
        String email = getString( getColumnIndex( UserTable.Cols.EMAIL ) );
        String pwd = getString( getColumnIndex( UserTable.Cols.PWD ) );
        return new User( email, pwd, id );
    }

}
