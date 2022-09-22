package com.example.assignment_1;

import androidx.lifecycle.MutableLiveData;

public class CommonData
{
    public MutableLiveData<Restaurant> selectedRestaurant;
    public MutableLiveData<Integer> loggedInUser;
    public MutableLiveData<FoodItem> selectedFoodItem;
    public MutableLiveData<Integer> qtyConfirmed;
    public MutableLiveData<Boolean> checkout;
    public MutableLiveData<Boolean> checkoutConfirm;
    public MutableLiveData<OrderList> orderList;

    public CommonData()
    {
        selectedRestaurant = new MutableLiveData<Restaurant>();
        selectedRestaurant.setValue(null);

        loggedInUser = new MutableLiveData<Integer>();
        loggedInUser.setValue(0);

        selectedFoodItem = new MutableLiveData<FoodItem>();
        selectedFoodItem.setValue(null);

        qtyConfirmed = new MutableLiveData<Integer>();
        qtyConfirmed.setValue(1);

        checkout = new MutableLiveData<Boolean>();
        checkout.setValue(false);

        checkoutConfirm = new MutableLiveData<Boolean>();
        checkoutConfirm.setValue(false);

        orderList = new MutableLiveData<OrderList>();
        orderList.setValue(null);
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

    public void setSelectedFoodItem( FoodItem value )
    {
        this.selectedFoodItem.setValue(value);
    }
    public FoodItem getSelectedFoodItem()
    {
        return selectedFoodItem.getValue();
    }

    public void setQtyConfirmed(int value)
    {
        this.qtyConfirmed.setValue(value);
    }
    public int getQtyConfirmed()
    {
        return this.qtyConfirmed.getValue();
    }

    public void setCheckout(Boolean value)
    {
        this.checkout.setValue(value);
    }
    public boolean getCheckout()
    {
        return this.checkout.getValue();
    }

    public void setCheckoutConfirm(Boolean value)
    {
        this.checkoutConfirm.setValue(value);
    }
    public Boolean getCheckoutConfirm()
    {
        return this.checkoutConfirm.getValue();
    }

    public void setOrderList(OrderList value)
    {
        this.orderList.setValue(value);
    }
    public OrderList getOrderList()
    {
        return this.orderList.getValue();
    }
}
