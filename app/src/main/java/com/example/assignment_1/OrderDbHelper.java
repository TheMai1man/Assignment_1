package com.example.assignment_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.assignment_1.OrderSchema.*;

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

        db.execSQL( "CREATE TABLE " + FoodItemTable.NAME + "(" +
                FoodItemTable.Cols.RES_ID + " INTEGER, " +
                FoodItemTable.Cols.ITEM_ID + " INTEGER, " +
                FoodItemTable.Cols.NAME + " TEXT, " +
                FoodItemTable.Cols.DESCRIPTION + " TEXT, " +
                FoodItemTable.Cols.PRICE + " REAL, " +
                FoodItemTable.Cols.PHOTO + " INTEGER)" );

        db.execSQL( "CREATE TABLE " + RestaurantTable.NAME + "(" +
                RestaurantTable.Cols.RES_ID + " INTEGER, " +
                RestaurantTable.Cols.NAME + " TEXT, " +
                RestaurantTable.Cols.LOGO + " INTEGER)" );

        db.execSQL( "CREATE TABLE " + OrderHistoryTable.NAME + "(" +
                OrderHistoryTable.Cols.USER_ID + " INTEGER, " +
                OrderHistoryTable.Cols.DATE_TIME + " TEXT, " +
                OrderHistoryTable.Cols.COST + " REAL, " +
                OrderHistoryTable.Cols.ORDER_ID + " INTEGER)" );

        db.execSQL( "CREATE TABLE " + OrderTable.NAME + "(" +
                OrderTable.Cols.ITEM_ID + " INTEGER, " +
                OrderTable.Cols.QTY + " INTEGER, " +
                OrderTable.Cols.ORDER_ID + " INTEGER)" );

        db.execSQL( "CREATE TABLE " + CurrentOrder.NAME + "(" +
                CurrentOrder.Cols.ITEM_ID + " INTEGER, " +
                CurrentOrder.Cols.QTY + " INTEGER)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2)
    {
        //coming soon...
    }
}
