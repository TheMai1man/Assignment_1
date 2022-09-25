package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.example.assignment_1.OrderSchema.*;

public class MainActivity extends AppCompatActivity
{
    Button order, login;
    private CommonData mViewModel;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.db = new OrderDbHelper( MainActivity.this ).getWritableDatabase();

        //viewModel for fragment and activity communication
        mViewModel = new ViewModelProvider(MainActivity.this).get(CommonData.class);

        //Multifunction Buttons which will hide and reappear during runtime
        order = findViewById(R.id.startOrder);
        login = findViewById(R.id.loginRegister);

        FragmentManager fm = getSupportFragmentManager();


        //login and history button handler
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if( mViewModel.getLoggedInUser() == null )
                {
                    fm.beginTransaction().replace(R.id.frameLayout, LoginFragment.class, null).commit();

                    mViewModel.loggedInUser.observe(MainActivity.this, new Observer<User>()
                    {
                        @Override
                        public void onChanged(User user)
                        {
                            setUpHome(fm);
                        }
                    });
                }
                else
                {
                    //order history function not yet added
                    Toast.makeText(MainActivity.this, "Order history unavailable.", Toast.LENGTH_LONG).show();
                }
            }
        });


        //Load specials home page fragment
        setUpHome(fm);

        //load Restaurant selection fragment
        order.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                fm.beginTransaction().replace( R.id.frameLayout, RestaurantFragment.class, null ).commit();

                order.setVisibility(View.GONE);
                login.setVisibility(View.GONE);
            }
        });


        //load food selection fragment when selectedRestaurant set in CommonData
        mViewModel.selectedRestaurant.observe(MainActivity.this, new Observer<Restaurant>()
        {
            @Override
            public void onChanged(Restaurant restaurant)
            {
                if(restaurant != null)
                {
                    //test that restaurant is set in CommonData
                    Restaurant r = mViewModel.getSelectedRestaurant();
                    //open MenuFragment in framelayout
                    fm.beginTransaction().replace( R.id.frameLayout, MenuFragment.class, null ).commit();
                }
            }
        });


        //User chooses a quantity for the selected FoodItem
        mViewModel.selectedFoodItem.observe(MainActivity.this, new Observer<FoodItem>()
        {
            @Override
            public void onChanged(FoodItem foodItem)
            {
                if(foodItem != null)
                {
                    fm.beginTransaction().replace( R.id.frameLayout, QuantityFragment.class, null ).commit();
                }

            }
        });


        //when qty confirmed we return to the menu
        mViewModel.qtyConfirmed.observe(MainActivity.this, new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                if(mViewModel.getQtyConfirmed() > 0)
                {
                    fm.beginTransaction().replace( R.id.frameLayout, MenuFragment.class, null ).commit();

                    if(mViewModel.getQtyConfirmed() ==  0 )
                    {
                        //toast: "qty '0' invalid, bucket unchanged"
                        Toast.makeText(MainActivity.this, "Bucket unchanged.", Toast.LENGTH_LONG).show();
                    }
                    if(mViewModel.getQtyConfirmed() >  0 )
                    {
                        //toast: "qty '0' invalid, bucket unchanged"
                        Toast.makeText(MainActivity.this, "Added to bucket.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        //user is done adding to cart, now load checkoutFragment for finalisation of order
        mViewModel.checkout.observe(MainActivity.this, new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                if( mViewModel.getCheckout() )
                {
                    if(mViewModel.getOrderList().size() > 0)
                    {
                        fm.beginTransaction().replace( R.id.frameLayout, CheckoutFragment.class, null ).commit();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Bucket is empty!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });



        mViewModel.loggedInUser.observe(MainActivity.this, new Observer<User>()
        {
            @Override
            public void onChanged(User user)
            {
                if(mViewModel.getSelectedRestaurant() != null)
                {
                    //finalise order
                    saveOrder(mViewModel.getLoggedInUser());
                    //notify user of completed order with toast
                    Toast.makeText(MainActivity.this, "Order complete!", Toast.LENGTH_LONG).show();
                    //return to MainActivity for home page
                    setUpHome(fm);
                }
            }
        });

        //confirm checkout, login/register if no user logged in
        mViewModel.checkoutConfirm.observe(MainActivity.this, new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                if( mViewModel.getCheckoutConfirm() )
                {
                    if( mViewModel.getLoggedInUser() == null )
                    {
                        fm.beginTransaction().replace( R.id.frameLayout, LoginFragment.class, null ).commit();
                        order.setVisibility(View.GONE);
                        login.setVisibility(View.GONE);
                    }
                    else
                    {
                        //finalise order
                        saveOrder(mViewModel.getLoggedInUser());
                        //notify user of completed order with toast
                        Toast.makeText(MainActivity.this, "Order complete!", Toast.LENGTH_LONG).show();
                        //return to MainActivity for home page
                        setUpHome(fm);
                    }
                }
            }
        });

    }


/*
    //loads the login fragment to get user to register or login
    public void getUserLoggedIn(FragmentManager fm)
    {
        fm.beginTransaction().replace( R.id.frameLayout, LoginFragment.class, null ).commit();
        order.setVisibility(View.GONE);
        login.setVisibility(View.GONE);
    }

 */


    //load specials fragment
    public void setUpHome(FragmentManager fm)
    {
        //set login button text based on user log in status
        if( mViewModel.getLoggedInUser() == null )
        {
            login.setText("Login");
        }
        else
        {
            login.setText("Order History");
        }

        fm.beginTransaction().replace( R.id.frameLayout, SpecialsFragment.class, null ).commit();

        order.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);

        mViewModel.setCheckout(false);
        mViewModel.setCheckoutConfirm(false);
        mViewModel.setOrderList(null);
    }

    public static Intent getIntent(Context c)
    {
        Intent intent = new Intent(c, MainActivity.class);
        return intent;
    }

    public void saveOrder(User user)
    {
        OrderList orderList = mViewModel.getOrderList();

        int ii = 0;
        int orderNum = newOrderNum();
        double totalPrice = 0;

        ContentValues cv = new ContentValues();

        while( ii < orderList.size() )
        {
            totalPrice += orderList.get(ii).getPrice();
            cv.put( OrderTable.Cols.ORDER_ID, orderNum );
            cv.put( OrderTable.Cols.QTY, orderList.get(ii).getQty() );
            cv.put( OrderTable.Cols.ITEM_ID, orderList.get(ii).getId() );
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
        MyCursor cursor = new MyCursor( db.query( OrderHistoryTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null) );

        return cursor.getCount() + 1;
    }

}