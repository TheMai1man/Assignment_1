package com.example.assignment_1;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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


        decrease = view.findViewById(R.id.decreaseQty);
        increase = view.findViewById(R.id.increaseQty);
        qty = view.findViewById(R.id.qty);
        name = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);
        photo = view.findViewById(R.id.imageView);
        confirm = view.findViewById(R.id.confirm);

        mViewModel = new ViewModelProvider(this).get(CommonData.class);
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

                }
                else
                {
                    //do not add to order
                }
            }
        });

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);
    }

}