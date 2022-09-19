package com.example.assignment_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantFragment extends Fragment
{
    private CommonData mViewModel;
    private RestaurantData data;

    public RestaurantFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_selector, ui, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.selectorRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager( getActivity(),
                LinearLayoutManager.VERTICAL, false) );

        if(data == null)
        {
            data = RestaurantData.get();
        }

        MyAdapter adapter = new MyAdapter(data);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);

        //handle user interaction here if outside of recycler view
    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataVHolder>
    {
        RestaurantData data;

        public MyAdapter(RestaurantData rd)
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
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new MyDataVHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(MyDataVHolder vh, int index)
        {
            vh.bind(data.get(index));

            vh.
            //here we can set anything specific to the viewHolder that may change during runtime
            //eg. with an onClickListener
        }
    }

    private static class MyDataVHolder extends RecyclerView.ViewHolder
    {
        private final TextView textView;
        private final ImageView imageView;

        public MyDataVHolder(LayoutInflater li, ViewGroup parent)
        {
            super( li.inflate(R.layout.restaurant_selection, parent, false) );
            textView = (TextView) itemView.findViewById(R.id.name);
            imageView = (ImageView) itemView.findViewById(R.id.logo);
        }

        public void bind(Restaurant data)
        {
            textView.setText(data.getName());
            imageView.setImageResource(data.getLogo());
        }
    }
}
