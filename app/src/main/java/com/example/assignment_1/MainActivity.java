package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
                    logIn(fm);
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


        //confirm checkout, login/register if no user logged in
        mViewModel.checkoutConfirm.observe(MainActivity.this, new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                if( mViewModel.getCheckoutConfirm() )
                {
                    completeOrder(fm);
                }
            }
        });

    }


    public void completeOrder(FragmentManager fm)
    {
        saveOrder();
        //return to MainActivity for home page
        setUpHome(fm);
        //notify user of completed order with toast
        Toast.makeText(MainActivity.this, "Order complete!", Toast.LENGTH_LONG).show();
    }

    public void logIn(FragmentManager fm)
    {
        order.setVisibility(View.GONE);
        login.setVisibility(View.GONE);
        fm.beginTransaction().replace( R.id.frameLayout, LoginFragment.class, null ).commit();
    }


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

        mViewModel.setCheckoutConfirm(false);
    }


    public void saveOrder()
    {
        OrderList list = mViewModel.getOrderList();
        User user = mViewModel.getLoggedInUser();

        int ii = 0;
        int orderNum = newOrderNum();
        double totalPrice = 0;

        ContentValues cv = new ContentValues();

        while( ii < list.size() )
        {
            totalPrice += list.get(ii).getPrice();
            cv.put( OrderTable.Cols.ORDER_ID, orderNum );
            cv.put( OrderTable.Cols.QTY, list.get(ii).getQty() );
            cv.put( OrderTable.Cols.ITEM_ID, list.get(ii).getId() );
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