package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button order, login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        order = findViewById(R.id.startOrder);
        login = findViewById(R.id.loginRegister);

        FragmentManager fm = getSupportFragmentManager();

        //specials home page

        SpecialsFragment specialsFragment = (SpecialsFragment) fm.findFragmentById(R.id.frameLayout);
        if(specialsFragment == null)
        {
            specialsFragment = new SpecialsFragment();
        }
        fm.beginTransaction().add(R.id.frameLayout, specialsFragment).commit();

        //load restaurant selection
        order.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                RestaurantFragment restaurantFragment = (RestaurantFragment) fm.findFragmentById(R.id.frameLayout);
                if(restaurantFragment == null)
                {
                    restaurantFragment = new RestaurantFragment();
                }
                fm.beginTransaction().add(R.id.frameLayout, restaurantFragment).commit();

                order.setVisibility(View.GONE);
                login.setVisibility(View.GONE);
            }
        });






        //load food selection

        //checkout, view/edit order

        //login, register
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //login or register
            }
        });
    }

}