package com.example.assignment_1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CommonData extends ViewModel
{
    public MutableLiveData<Restaurant> selectedRestaurant;
    public MutableLiveData<User> loggedInUser;
    public MutableLiveData<FoodItem> selectedFoodItem;
    public MutableLiveData<Integer> qtyConfirmed;
    public MutableLiveData<Boolean> checkout;
    public MutableLiveData<Boolean> checkoutConfirm;
    public MutableLiveData<OrderList> orderList;
    public MutableLiveData<UserList> userList;
    public MutableLiveData<Boolean> loggedIn;

    public CommonData()
    {
        selectedRestaurant = new MutableLiveData<Restaurant>();
        selectedRestaurant.setValue(null);

        loggedInUser = new MutableLiveData<User>();
        loggedInUser.setValue(null);

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

        userList = new MutableLiveData<UserList>();
        userList.setValue(null);

        loggedIn = new MutableLiveData<Boolean>();
        loggedIn.setValue(false);
    }

    public boolean getLoggedIn()
    {
        return this.loggedIn.getValue();
    }
    public void setLoggedIn(Boolean value)
    {
        this.loggedIn.setValue(value);
    }

    public Restaurant getSelectedRestaurant()
    {
        return this.selectedRestaurant.getValue();
    }
    public void setSelectedRestaurant(Restaurant value)
    {
        this.selectedRestaurant.setValue(value);
    }

    public User getLoggedInUser()
    {
        return this.loggedInUser.getValue();
    }
    public void setLoggedInUser(User value)
    {
        this.loggedInUser.setValue(value);
        setLoggedIn(true);
    }

    public void setSelectedFoodItem( FoodItem value )
    {
        this.selectedFoodItem.setValue(value);
    }
    public FoodItem getSelectedFoodItem()
    {
        return this.selectedFoodItem.getValue();
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

    public void setUserList( UserList value )
    {
        this.userList.setValue(value);
    }
    public UserList getUserList()
    {
        return this.userList.getValue();
    }

}
