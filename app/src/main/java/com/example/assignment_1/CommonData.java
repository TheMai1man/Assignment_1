package com.example.assignment_1;

import androidx.lifecycle.MutableLiveData;

public class CommonData
{
    public MutableLiveData<Restaurant> selectedRestaurant;
    public MutableLiveData<Integer> loggedInUser;

    public CommonData()
    {
        selectedRestaurant = new MutableLiveData<Restaurant>();
        selectedRestaurant.setValue(null);

        loggedInUser = new MutableLiveData<Integer>();
        loggedInUser.setValue(null);
    }

    public Restaurant getSelectedRestaurant()
    {
        return selectedRestaurant.getValue();
    }

    public void setSelectedRestaurant(Restaurant value)
    {
        this.selectedRestaurant.setValue(value);
    }

    public int getLoggedInUser()
    {
        return loggedInUser.getValue();
    }

    public void setLoggedInUser(int value)
    {
        this.loggedInUser.setValue(value);
    }
}
