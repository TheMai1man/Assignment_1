package com.example.assignment_1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuantityFragment extends Fragment
{
    private Button decrease, increase, confirm;
    private EditText qty;
    private TextView name, description;
    private ImageView photo;

    private CommonData mViewModel;
    private FoodItem data;
    int ii;

    public QuantityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        return inflater.inflate(R.layout.fragment_quantity, ui, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);

        decrease = (Button) view.findViewById(R.id.decreaseQty);
        increase = (Button) view.findViewById(R.id.increaseQty);
        qty = (EditText) view.findViewById(R.id.qty);
        name = (TextView) view.findViewById(R.id.name);
        description = (TextView) view.findViewById(R.id.description);
        photo = (ImageView) view.findViewById(R.id.imageView);
        confirm = (Button) view.findViewById(R.id.confirm);

        if(mViewModel.getOrderList() == null)
        {
            mViewModel.setOrderList(new OrderList());
        }

        data = mViewModel.getSelectedFoodItem();

        ii = 1;
        qty.setText("1");
        name.setText( data.getName() );
        description.setText( data.getDescription() );
        photo.setImageResource( data.getPhoto() );

        increase.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ii++;
                qty.setText(String.valueOf(ii));
            }
        });

        decrease.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(ii > 0)
                {
                    ii--;
                    qty.setText(String.valueOf(ii));
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(ii > 0)
                {
                    mViewModel.getOrderList().add( new Order(data, ii) );

                    mViewModel.setQtyConfirmed(ii);
                }
                else if( ii < 0 )
                {
                    //do nothing, pop toast to user
                    Toast.makeText(getActivity(), "Quantity invalid!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    mViewModel.setQtyConfirmed(ii);
                    //pop toast to user in MainActivity
                }
            }
        });

    }

}