package com.example.assignment_1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MenuFragment extends Fragment
{
    private CommonData mViewModel;
    private Restaurant data;

    private Button checkout;

    public MenuFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        return inflater.inflate(R.layout.fragment_menu, ui, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.menuRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager( requireActivity(),
                LinearLayoutManager.VERTICAL, false ));

        if(data == null)
        {
            data = mViewModel.getSelectedRestaurant();
        }

        checkout = (Button) view.findViewById(R.id.checkoutButton);

        MyAdapter adapter = new MyAdapter(data);
        rv.setAdapter(adapter);

        checkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if( mViewModel.getOrderList() != null )
                {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, CheckoutFragment.class, null).commit();
                }
                else
                {
                    Toast.makeText(requireActivity(), "Your bucket is empty!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataVHolder>
    {
        Restaurant data;

        public MyAdapter(Restaurant r)
        {
            this.data = r;
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }

        @NonNull
        @Override
        public MyDataVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(requireActivity());
            return new MyDataVHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(MyDataVHolder vh, int index)
        {
            FoodItem fi = data.get(index);
            vh.bind(fi);

            vh.foodView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //set selected FoodItem
                    mViewModel.setSelectedFoodItem(fi);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, QuantityFragment.class, null).commit();
                    //pop up quantity selection and add to order
                }
            });
        }
    }

    private static class MyDataVHolder extends RecyclerView.ViewHolder
    {
        private final TextView nameView, priceView, descView;
        private final ImageView imageView;
        private final ConstraintLayout foodView;

        public MyDataVHolder(LayoutInflater li, ViewGroup parent)
        {
            super( li.inflate(R.layout.food_selection, parent, false) );
            nameView = (TextView) itemView.findViewById(R.id.name);
            priceView = (TextView) itemView.findViewById(R.id.price);
            descView = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.photo);
            foodView = (ConstraintLayout) itemView.findViewById(R.id.foodSelection);
        }

        public void bind(FoodItem data)
        {
            nameView.setText(data.getName());
            priceView.setText( String.format("%.2f", data.getPrice() ) );
            descView.setText(data.getDescription());
            imageView.setImageResource(data.getPhoto());
        }
    }
}
