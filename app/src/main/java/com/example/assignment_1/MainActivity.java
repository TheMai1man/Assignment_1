package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button order, login;
    private CommonData mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewModel for fragment communication
        mViewModel = new ViewModelProvider(this).get(CommonData.class);

        //Multifunction Buttons which will hide and reappear during runtime
        order = findViewById(R.id.startOrder);
        login = findViewById(R.id.loginRegister);

        FragmentManager fm = getSupportFragmentManager();


        //Load specials home page fragment
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
        //set listener for selectedRestaurant in CommonData
        mViewModel.selectedRestaurant.observe(this, new Observer<Restaurant>()
        {
            @Override
            public void onChanged(Restaurant restaurant)
            {
                MenuFragment menuFragement = (MenuFragment) fm.findFragmentById(R.id.frameLayout);
                if(menuFragement == null)
                {
                    menuFragement = new MenuFragment();
                }
                fm.beginTransaction().add(R.id.frameLayout, menuFragement).commit();

                order.setVisibility(View.GONE);
                login.setVisibility(View.GONE);
            }
        });

        //User chooses a quantity for the selected FoodItem
        mViewModel.selectedFoodItem.observe(this, new Observer<FoodItem>()
        {
            @Override
            public void onChanged(FoodItem foodItem)
            {
                QuantityFragment quantityFragment = (QuantityFragment) fm.findFragmentById(R.id.frameLayout);
                if(quantityFragment == null)
                {
                    quantityFragment = new QuantityFragment();
                }
                fm.beginTransaction().add(R.id.frameLayout, quantityFragment).commit();


            }
        });


        //checkout, view/edit order



        //login, register
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int userID = 0;
                //login or register
                //load login Activity for result

                //set user ID in CommonData
                if(userID != 0)
                {
                    mViewModel.setLoggedInUser(userID);
                }

                //change button text to "order history"
                login.setText("Order History");
            }
        });
    }

}