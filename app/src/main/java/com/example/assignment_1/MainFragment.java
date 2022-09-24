package com.example.assignment_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainFragment extends Fragment
{
    private CommonData mViewModel;

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main,ui, false);

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setReorderingAllowed(true);

        //load restaurant selection fragment
        transaction.replace( R.id.frameLayout, RestaurantFragment.class, null ).commit();

        //load food selection fragment when selectedRestaurant set in CommonData
        mViewModel.selectedRestaurant.observe(requireActivity(), new Observer<Restaurant>()
        {
            @Override
            public void onChanged(Restaurant restaurant)
            {
                transaction.replace( R.id.frameLayout, MenuFragment.class, null ).commit();
            }
        });


        //User chooses a quantity for the selected FoodItem
        mViewModel.selectedFoodItem.observe(requireActivity(), new Observer<FoodItem>()
        {
            @Override
            public void onChanged(FoodItem foodItem)
            {
                transaction.replace( R.id.frameLayout, QuantityFragment.class, null ).commit();
            }
        });


        //when qty confirmed we return to the menu
        mViewModel.qtyConfirmed.observe(requireActivity(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                transaction.replace( R.id.frameLayout, MenuFragment.class, null ).commit();

                if(mViewModel.getQtyConfirmed() ==  0 )
                {
                    //toast: "qty '0' invalid, bucket unchanged"
                    Toast.makeText(requireActivity(), "Bucket unchanged.", Toast.LENGTH_LONG).show();
                }

            }
        });


        //user is done adding to cart, now load checkoutFragment for finalisation of order
        mViewModel.checkout.observe(requireActivity(), new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                if( mViewModel.getCheckout() )
                {
                    transaction.replace( R.id.frameLayout, CheckoutFragment.class, null ).commit();
                }
            }
        });


        //confirm checkout, login/register if no user logged in
        mViewModel.checkoutConfirm.observe(requireActivity(), new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                if( mViewModel.getCheckoutConfirm() )
                {
                    if( !mViewModel.getLoggedIn() )
                    {
                        getUserLoggedIn(transaction);
                        mViewModel.loggedInUser.observe(requireActivity(), new Observer<User>()
                        {
                            @Override
                            public void onChanged(User user)
                            {
                                //finalise order
                                mViewModel.getOrderList().saveOrder(mViewModel.getLoggedInUser());
                                //notify user of completed order with toast
                                Toast.makeText(requireActivity(), "Order complete!", Toast.LENGTH_LONG).show();
                                //return to MainActivity for home page
                                Intent intent = MainActivity.getIntent(requireActivity());
                                startActivity(intent);
                            }
                        });
                    }
                    else
                    {
                        //finalise order
                        mViewModel.getOrderList().saveOrder(mViewModel.getLoggedInUser());
                        //notify user of completed order with toast
                        Toast.makeText(requireActivity(), "Order complete!", Toast.LENGTH_LONG).show();
                        //return to MainActivity for home page
                        Intent intent = MainActivity.getIntent(requireActivity());
                        startActivity(intent);
                    }
                }
            }
        });


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);
    }

    public void getUserLoggedIn(FragmentTransaction transaction)
    {
        transaction.replace( R.id.frameLayout, LoginFragment.class, null ).commit();
    }
}
