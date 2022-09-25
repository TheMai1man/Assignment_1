package com.example.assignment_1;

import android.content.ContentValues;
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

        //Here we hard code adding restaurants and their menu items to the database
        Restaurant restautantArray[] = new Restaurant[] {
                new Restaurant( 1, "place1", R.drawable.logo1 ),
                new Restaurant( 2, "place2", R.drawable.logo2 ),
                new Restaurant( 3, "place3", R.drawable.logo3 ),
                new Restaurant( 4, "place4", R.drawable.logo4 ),
                new Restaurant( 5, "place5", R.drawable.logo5 ),
                new Restaurant( 6, "place6", R.drawable.logo6 ),
                new Restaurant( 7, "place7", R.drawable.logo7 ),
                new Restaurant( 8, "place8", R.drawable.logo8 ),
                new Restaurant( 9, "place9", R.drawable.logo9 ),
                new Restaurant( 10, "place10", R.drawable.logo10 ),
                new Restaurant( 11, "place11", R.drawable.logo11 )
        };

        FoodItem itemArray[] = new FoodItem[] {
                new FoodItem( 1, 1, "food1", "food1 description", 19.99, R.drawable.food1 ),
                new FoodItem( 1, 2, "food2", "food2 description", 4.99, R.drawable.food2 ),
                new FoodItem( 1, 3, "food3", "food3 description", 9.99, R.drawable.food3 ),
                new FoodItem( 1, 4, "food4", "food4 description", 19.99, R.drawable.food4 ),
                new FoodItem( 1, 5, "food5", "food5 description", 14.99, R.drawable.food5 ),
                new FoodItem( 1, 6, "food6", "food6 description", 19.99, R.drawable.food6 ),
                new FoodItem( 2, 7, "food7", "food7 description", 19.99, R.drawable.food7 ),
                new FoodItem( 2, 8, "food8", "food8 description", 19.99, R.drawable.food8 ),
                new FoodItem( 2, 9, "food9", "food9 description", 19.99, R.drawable.food9),
                new FoodItem( 2, 10, "food10", "food10 description", 19.99, R.drawable.food10 ),
                new FoodItem( 2, 11, "food11", "food11 description", 19.99, R.drawable.food11 ),
                new FoodItem( 2, 12, "food12", "food12 description", 14.99, R.drawable.food12 ),
                new FoodItem( 3, 13, "food13", "food13 description", 9.99, R.drawable.food13 ),
                new FoodItem( 3, 14, "food14", "food14 description", 19.99, R.drawable.food14 ),
                new FoodItem( 3, 15, "food15", "food15 description", 14.99, R.drawable.food15 ),
                new FoodItem( 3, 16, "food16", "food16 description", 19.99, R.drawable.food16 ),
                new FoodItem( 3, 17, "food17", "food17 description", 14.99, R.drawable.food17 ),
                new FoodItem( 3, 18, "food18", "food18 description", 199.99, R.drawable.food18 ),
                new FoodItem( 4, 19, "food19", "food19 description", 19.99, R.drawable.food19 ),
                new FoodItem( 4, 20, "food20", "food20 description", 19.99, R.drawable.food20 ),
                new FoodItem( 4, 21, "food21", "food21 description", 4.99, R.drawable.food21 ),
                new FoodItem( 4, 22, "food22", "food22 description", 14.99, R.drawable.food22 ),
                new FoodItem( 4, 23, "food23", "food23 description", 19.99, R.drawable.food23 ),
                new FoodItem( 4, 24, "food24", "food24 description", 9.99, R.drawable.food24 ),
                new FoodItem( 5, 25, "food25", "food25 description", 19.99, R.drawable.food25 ),
                new FoodItem( 5, 26, "food26", "food26 description", 14.99, R.drawable.food26 ),
                new FoodItem( 5, 27, "food27", "food27 description", 19.99, R.drawable.food27 ),
                new FoodItem( 5, 28, "food28", "food28 description", 9.99, R.drawable.food28 ),
                new FoodItem( 5, 29, "food29", "food29 description", 19.99, R.drawable.food29 ),
                new FoodItem( 5, 30, "food30", "food30 description", 4.99, R.drawable.food30 ),
                new FoodItem( 6, 31, "food31", "food31 description", 14.99, R.drawable.food31 ),
                new FoodItem( 6, 32, "food32", "food32 description", 19.99, R.drawable.food32 ),
                new FoodItem( 6, 33, "food33", "food33 description", 9.99, R.drawable.food33 ),
                new FoodItem( 6, 34, "food34", "food34 description", 19.99, R.drawable.food34 ),
                new FoodItem( 6, 35, "food35", "food35 description", 19.99, R.drawable.food35 ),
                new FoodItem( 6, 36, "food36", "food36 description", 14.99, R.drawable.food36 ),
                new FoodItem( 7, 37, "food37", "food37 description", 199.99, R.drawable.food37 ),
                new FoodItem( 7, 38, "food38", "food38 description", 19.99, R.drawable.food38 ),
                new FoodItem( 7, 39, "food39", "food39 description", 19.99, R.drawable.food39 ),
                new FoodItem( 7, 40, "food40", "food40 description", 19.99, R.drawable.food40 ),
                new FoodItem( 7, 41, "food41", "food41 description", 19.99, R.drawable.food41 ),
                new FoodItem( 7, 42, "food42", "food42 description", 9.99, R.drawable.food42 ),
                new FoodItem( 8, 43, "food43", "food43 description", 19.99, R.drawable.food43 ),
                new FoodItem( 8, 44, "food44", "food44 description", 4.99, R.drawable.food44 ),
                new FoodItem( 8, 45, "food45", "food45 description", 199.99, R.drawable.food45 ),
                new FoodItem( 8, 46, "food46", "food46 description", 19.99, R.drawable.food46 ),
                new FoodItem( 8, 47, "food47", "food47 description", 9.99, R.drawable.food47 ),
                new FoodItem( 8, 48, "food48", "food48 description", 19.99, R.drawable.food48 ),
                new FoodItem( 9, 49, "food49", "food49 description", 19.99, R.drawable.food49 ),
                new FoodItem( 9, 50, "food50", "food50 description", 199.99, R.drawable.food50 ),
                new FoodItem( 9, 51, "food51", "food51 description", 19.99, R.drawable.food51 ),
                new FoodItem( 9, 52, "food52", "food52 description", 14.99, R.drawable.food52 ),
                new FoodItem( 9, 53, "food53", "food53 description", 19.99, R.drawable.food53 ),
                new FoodItem( 9, 54, "food54", "food54 description", 4.99, R.drawable.food54 ),
                new FoodItem( 10, 55, "food55", "food55 description", 19.99, R.drawable.food55 ),
                new FoodItem( 10, 56, "food56", "food56 description", 199.99, R.drawable.food56 ),
                new FoodItem( 10, 57, "food57", "food57 description", 19.99, R.drawable.food57 ),
                new FoodItem( 10, 58, "food58", "food58 description", 9.99, R.drawable.food58 ),
                new FoodItem( 10, 59, "food59", "food59 description", 4.99, R.drawable.food59 ),
                new FoodItem( 10, 60, "food60", "food60 description", 14.99, R.drawable.food60 ),
                new FoodItem( 11, 61, "food61", "food61 description", 9.99, R.drawable.food61 ),
                new FoodItem( 11, 62, "food62", "food62 description", 199.99, R.drawable.food62 ),
                new FoodItem( 11, 63, "food63", "food63 description", 19.99, R.drawable.food63 ),
                new FoodItem( 11, 64, "food64", "food64 description", 19.99, R.drawable.food64 ),
                new FoodItem( 11, 65, "food65", "food65 description", 9.99, R.drawable.food65 )
        };


        FoodItem item;
        Restaurant restaurant;

        for(int ii = 0; ii < restautantArray.length; ii++ )
        {
            restaurant = restautantArray[ii];

            ContentValues cv = new ContentValues();
            cv.put( RestaurantTable.Cols.NAME, restaurant.getName() );
            cv.put( RestaurantTable.Cols.LOGO, restaurant.getLogo() );
            cv.put( RestaurantTable.Cols.RES_ID, restaurant.getResID() );

            db.insert( RestaurantTable.NAME, null, cv );
        }

        for(int ii = 0; ii < itemArray.length; ii++)
        {
            item = itemArray[ii];

            ContentValues cv = new ContentValues();
            cv.put( FoodItemTable.Cols.ITEM_ID, item.getItemID() );
            cv.put( FoodItemTable.Cols.RES_ID, item.getResID() );
            cv.put( FoodItemTable.Cols.NAME, item.getName() );
            cv.put( FoodItemTable.Cols.DESCRIPTION, item.getDescription() );
            cv.put( FoodItemTable.Cols.PRICE, item.getPrice() );
            cv.put( FoodItemTable.Cols.PHOTO, item.getPhoto() );

            db.insert( FoodItemTable.NAME, null, cv );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2)
    {
        //TBA
    }
}
