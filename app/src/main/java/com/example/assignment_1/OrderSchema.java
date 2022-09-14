package com.example.assignment_1;

public class OrderSchema
{
    public static class OrderTable
    {
        //name will change to reflect order number
        public static class Cols
        {
            public static final String ITEM_ID = "item_id";
            public static final String QTY = "qty";
        }
    }

    public static class OrderHistoryTable
    {
        public static final String NAME = "orders";
        public static class Cols
        {
            public static final String USER_ID = "user_id";
            public static final String DATE_TIME = "date_time";
            public static final String COST = "cost";
            public static final String ORDER_ID = "order_id";
        }
    }

    public static class RestaurantTable
    {
        public static final String NAME = "restaurants";
        public static class Cols
        {
            public static final String RES_ID = "res_id";
            public static final String NAME = "res_name";
            public static final String LOGO = "logo";
        }
    }

    public static class FoodItemTable
    {
        public static final String NAME = "foodItems";
        public static class Cols
        {
            public static final String RES_ID = "res_id";
            public static final String ITEM_ID = "item_id";
            public static final String NAME = "item_name";
            public static final String DESCRIPTION = "description";
            public static final String PRICE = "price";
            public static final String PHOTO = "photo";
        }
    }

    public static class UserTable
    {
        public static final String NAME = "users";
        public static class Cols
        {
            public static final String USER_ID = "user_id";
            public static final String EMAIL = "email";
            public static final String PWD = "pwd";
        }
    }
}
