package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button order, login;
    private CommonData mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewModel for fragment and activity communication
        mViewModel = new ViewModelProvider(MainActivity.this).get(CommonData.class);

        //Multifunction Buttons which will hide and reappear during runtime
        order = findViewById(R.id.startOrder);
        login = findViewById(R.id.loginRegister);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setReorderingAllowed(true);

        //Load specials home page fragment
        setUpHome(transaction);

        //load MainFragment
        order.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                transaction.replace( R.id.frameLayout, MainFragment.class, null ).commit();
            }
        });


        //login and history button handler
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if( mViewModel.getLoggedInUser() == null )
                {
                    getUserLoggedIn(transaction);
                    mViewModel.loggedInUser.observe(MainActivity.this, new Observer<User>()
                    {
                        @Override
                        public void onChanged(User user)
                        {
                            setUpHome(transaction);
                        }
                    });
                }
                else
                {
                    //order history function not yet added
                    Toast.makeText(MainActivity.this, "Order history unavailable.", Toast.LENGTH_LONG).show();
                    /*
                    //hide order and login buttons
                    order.setVisibility(View.GONE);
                    login.setVisibility(View.GONE);

                    //order history of logged in user
                    HistoryFragment historyFragment = (HistoryFragment) fm.findFragmentById(R.id.frameLayout);
                    if(historyFragment == null)
                    {
                        historyFragment = new HisoryFragment();
                    }
                    fm.beginTransaction().add(R.id.frameLayout, historyFragment).commit();
                }
                */
                }
            }
        });

    }



    //loads the login fragment to get user to register or login
    public void getUserLoggedIn(FragmentTransaction transaction)
    {
        transaction.replace( R.id.frameLayout, LoginFragment.class, null ).commit();

        order.setVisibility(View.GONE);
        login.setVisibility(View.GONE);


    }


    //load specials fragment
    public void setUpHome(FragmentTransaction transaction)
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

        transaction.replace( R.id.frameLayout, SpecialsFragment.class, null ).commit();

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
}