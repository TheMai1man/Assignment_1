package com.example.assignment_1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
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
    Button decrease, increase, confirm;
    EditText qty;
    TextView name, description;
    ImageView photo;

    CommonData mViewModel;
    FoodItem data;
    int ii;

    public QuantityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_quantity, ui, false);

        decrease = (Button) view.findViewById(R.id.decreaseQty);
        increase = (Button) view.findViewById(R.id.increaseQty);
        qty = (EditText) view.findViewById(R.id.qty);
        name = (TextView) view.findViewById(R.id.name);
        description = (TextView) view.findViewById(R.id.description);
        photo = (ImageView) view.findViewById(R.id.imageView);
        confirm = (Button) view.findViewById(R.id.confirm);

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
                qty.setText(ii);
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
                    qty.setText(ii);
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
                    mViewModel.getOrderList().add(mViewModel.getSelectedFoodItem(), ii);


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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);
    }

}