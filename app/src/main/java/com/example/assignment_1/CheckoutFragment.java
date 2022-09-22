package com.example.assignment_1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CheckoutFragment extends Fragment
{
    private Button checkout;

    private CommonData mViewModel;
    private OrderList data;

    public CheckoutFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_checkout, ui, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.menuRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager( getActivity(),
                LinearLayoutManager.VERTICAL, false ));

        checkout = (Button) view.findViewById(R.id.checkoutButton);

        if(data == null)
        {
            data = mViewModel.getOrderList();
        }

        MyAdapter adapter = new MyAdapter(data);
        rv.setAdapter(adapter);

        checkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mViewModel.setCheckoutConfirm(true);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState )
    {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataVHolder>
    {
        OrderList data;

        public MyAdapter(OrderList o)
        {
            this.data = o;
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
            Order item = data.get(index);
            vh.bind(item);

            //onClickListener for remove
            //qty edit handling

        }
    }

    private static class MyDataVHolder extends RecyclerView.ViewHolder
    {
        private final TextView name, servePrice, totalPrice;
        private final Button remove;
        private final EditText qtyEdit;

        public MyDataVHolder(LayoutInflater li, ViewGroup parent)
        {
            super( li.inflate(R.layout.checkout_selection, parent, false) );
            name = (TextView) itemView.findViewById(R.id.name);
            servePrice = (TextView) itemView.findViewById(R.id.servingPrice);
            totalPrice = (TextView) itemView.findViewById(R.id.totalPrice);
            remove = (Button) itemView.findViewById(R.id.removeButton);
            qtyEdit = (EditText) itemView.findViewById(R.id.qtyEdit);
        }

        public void bind(Order data)
        {
            double price = data.getPrice();
            int qty = data.getQty();
            double total =  (double)qty * price;
            name.setText(data.getName());
            servePrice.setText( String.format("%.2f", price ) );
            totalPrice.setText( String.format("%.2f", total ) );
            qtyEdit.setText(qty);
        }
    }

}