package com.example.assignment_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.assignment_1.OrderSchema.FoodItemTable;
import com.example.assignment_1.OrderSchema.OrderTable;
import com.example.assignment_1.OrderSchema.OrderHistoryTable;
import com.example.assignment_1.OrderSchema.RestaurantTable;
import com.example.assignment_1.OrderSchema.UserTable;

public class OrderDbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "orders.db";

    public OrderDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL( "CREATE TABLE " + UserTable.NAME + "(" +
                UserTable.Cols.USER_ID + " INTEGER, " +
                UserTable.Cols.EMAIL + " TEXT, " +
                UserTable.Cols.PWD + " TEXT)" );

        db.execSQL( "CREATE TABLE " +  );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2)
    {

    }
}
