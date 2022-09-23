package com.example.assignment_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.assignment_1.OrderSchema.*;

import java.util.ArrayList;
import java.util.List;

public class RestaurantList
{
    private List<Restaurant> restaurantList = new ArrayList<>();
    private SQLiteDatabase db;

    public RestaurantList() {}

    public void load(Context context)
    {
        this.db = new OrderDbHelper( context.getApplicationContext() ).getWritableDatabase();

        MyCursor cursorR = new MyCursor( db.query( RestaurantTable.NAME,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null) );

        try
        {
            cursorR.moveToFirst();
            while( !cursorR.isAfterLast() )
            {
                Restaurant r = cursorR.getRestaurant();

                MyCursor cursorF = new MyCursor(db.query( FoodItemTable.NAME,
                                null,
                                RestaurantTable.Cols.RES_ID + "=" + r.getResID(),
                                null,
                                null,
                                null,
                                null) );
                try
                {
                    cursorF.moveToFirst();
                    while( !cursorF.isAfterLast() )
                    {
                        r.add(cursorF.getMenuItem());
                        cursorF.moveToNext();
                    }
                    restaurantList.add( r );
                }
                finally
                {
                    cursorF.close();
                }

                cursorR.moveToNext();
            }
        }
        finally
        {
            cursorR.close();
        }
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
