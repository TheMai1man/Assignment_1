package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.assignment_1.OrderSchema.*;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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

        //viewModel for fragment and activity communication
        mViewModel = new ViewModelProvider(this).get(CommonData.class);

        //Multifunction Buttons which will hide and reappear during runtime
        order = findViewById(R.id.startOrder);
        login = findViewById(R.id.loginRegister);

        this.db = new OrderDbHelper( this.getApplicationContext() ).getWritableDatabase();
        FragmentManager fm = getSupportFragmentManager();

        //Load specials home page fragment
        SpecialsFragment specialsFragment = (SpecialsFragment) fm.findFragmentById(R.id.frameLayout);
        if(specialsFragment == null)
        {
            specialsFragment = new SpecialsFragment();
        }
        fm.beginTransaction().add(R.id.frameLayout, specialsFragment).commit();


        //load restaurant selection fragment
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


        //load food selection fragment when selectedRestaurant set in CommonData
        mViewModel.selectedRestaurant.observe(this, new Observer<Restaurant>()
        {
            @Override
            public void onChanged(Restaurant restaurant)
            {
                MenuFragment menuFragment = (MenuFragment) fm.findFragmentById(R.id.frameLayout);
                if(menuFragment == null)
                {
                    menuFragment = new MenuFragment();
                }
                fm.beginTransaction().add(R.id.frameLayout, menuFragment).commit();
            }
        });


        //User chooses a quantity for the selected FoodItem
        mViewModel.selectedFoodItem.observe(this, new Observer<FoodItem>()
        {
            @Override
            public void onChanged(FoodItem foodItem)
            {
                if(mViewModel.getSelectedFoodItem() != null )
                {
                    QuantityFragment quantityFragment = (QuantityFragment) fm.findFragmentById(R.id.frameLayout);
                    if(quantityFragment == null)
                    {
                        quantityFragment = new QuantityFragment();
                    }
                    fm.beginTransaction().add(R.id.frameLayout, quantityFragment).commit();
                }
            }
        });


        //when qty confirmed we return to the menu
        mViewModel.qtyConfirmed.observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                MenuFragment menuFragment = (MenuFragment) fm.findFragmentById(R.id.frameLayout);
                if(menuFragment == null)
                {
                    menuFragment = new MenuFragment();
                }
                fm.beginTransaction().add(R.id.frameLayout, menuFragment).commit();


                if(mViewModel.getQtyConfirmed() ==  0 )
                {
                    //toast: "qty '0' invalid, bucket unchanged"
                }

            }
        });


        //user is done adding to cart, now load checkoutFragment for finalisation of order
        mViewModel.checkout.observe(this, new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                if( mViewModel.getCheckout() )
                {
                    CheckoutFragment checkoutFragment = (CheckoutFragment) fm.findFragmentById(R.id.frameLayout);
                    if(checkoutFragment == null)
                    {
                        checkoutFragment = new CheckoutFragment();
                    }
                    fm.beginTransaction().add(R.id.frameLayout, checkoutFragment).commit();
                }
            }
        });


        //confirm checkout, login/register if no user logged in
        mViewModel.checkoutConfirm.observe(this, new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                if( mViewModel.getCheckoutConfirm() )
                {
                    if( mViewModel.getLoggedInUser() == 0 )
                    {
                        getUserLoggedIn();
                        mViewModel.loggedInUser.observe(this, new Observer<Integer>()
                        {
                            @Override
                            public void onChanged(Integer integer)
                            {
                                //finalise order

                                //return to specials/home page
                                setUpHome();
                            }
                        });
                    }
                    else
                    {
                        //finalise order

                        //return to specials/home page
                        setUpHome();

                    }
                }
            }
        });


        //login button handler
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if( mViewModel.getLoggedInUser() == 0 )
                {
                    getUserLoggedIn();
                }
                else
                {
                    //order history of logged in user
                }
            }
        });

    }


    //loads the login fragment to get user to register or login
    public void getUserLoggedIn()
    {
        FragmentManager fm = getSupportFragmentManager();
        LoginFragment loginFragment = (LoginFragment) fm.findFragmentById(R.id.frameLayout);
        if(loginFragment == null)
        {
            loginFragment = new LoginFragment();
        }
        fm.beginTransaction().add(R.id.frameLayout, loginFragment).commit();
    }


    //load specials fragment
    public void setUpHome()
    {
        FragmentManager fm = getSupportFragmentManager();
        SpecialsFragment specialsFragment = (SpecialsFragment) fm.findFragmentById(R.id.frameLayout);
        if(specialsFragment == null)
        {
            specialsFragment = new SpecialsFragment();
        }
        fm.beginTransaction().add(R.id.frameLayout, specialsFragment).commit();

        //show order and login buttons
        order.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);

        //set login button text based on user log in status
        if( mViewModel.getLoggedInUser() == 0 )
        {
            login.setText("Login");
        }
        else
        {
            login.setText("Order History");
        }

        mViewModel.setCheckout(false);
        mViewModel.setCheckoutConfirm(false);
        mViewModel.setOrderList(null);
    }
}