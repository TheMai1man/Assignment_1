package com.example.assignment_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantFragment extends Fragment
{
    private CommonData mViewModel;
    private RestaurantList data;

    public RestaurantFragment() {}

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        data = new RestaurantList();
        data.load(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        return inflater.inflate(R.layout.fragment_selector, ui, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.selectorRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager( getActivity(),
                LinearLayoutManager.VERTICAL, false) );

        if(data == null)
        {
            data = new RestaurantList();
        }

        MyAdapter adapter = new MyAdapter(data);
        rv.setAdapter(adapter);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataVHolder>
    {
        RestaurantList data;

        public MyAdapter(RestaurantList rd)
        {
            this.data = rd;
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
            Restaurant r = data.get(index);
            vh.bind(r);

            vh.restaurantView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mViewModel.setSelectedRestaurant(r);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, MenuFragment.class, null).commit();
                }
            });
        }
    }

    private static class MyDataVHolder extends RecyclerView.ViewHolder
    {
        private final TextView textView;
        private final ImageView imageView;
        private final ConstraintLayout restaurantView;

        public MyDataVHolder(LayoutInflater li, ViewGroup parent)
        {
            super( li.inflate(R.layout.restaurant_selection, parent, false) );
            textView = (TextView) itemView.findViewById(R.id.name);
            imageView = (ImageView) itemView.findViewById(R.id.logo);
            restaurantView = (ConstraintLayout) itemView.findViewById(R.id.restaurantSelection);
        }

        public void bind(Restaurant data)
        {
            textView.setText(data.getName());
            imageView.setImageResource(data.getLogo());
        }
    }
}
