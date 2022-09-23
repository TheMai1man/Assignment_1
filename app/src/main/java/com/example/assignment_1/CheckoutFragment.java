package com.example.assignment_1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckoutFragment extends Fragment
{
    private Button checkout;

    private CommonData mViewModel;
    private OrderList data;

    public CheckoutFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
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

        data = mViewModel.getOrderList();

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
            vh.remove.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mViewModel.getOrderList().remove(item);
                }
            });

            //qty edit handling
            vh.qtyEdit.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void afterTextChanged(Editable editable)
                {
                    String input = vh.qtyEdit.getText().toString();
                    if( !input.equals("") )
                    {
                        int qty = Integer.parseInt(input);
                        if(qty > 0)
                        {
                            mViewModel.getOrderList().edit(item, qty);
                        }
                        else
                        {
                            //toast to user, "serving quantity invalid"
                            Toast.makeText(getActivity(), "Quantity invalid!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        //toast to user, "serving quantity invalid"
                        Toast.makeText(getActivity(), "Quantity invalid!", Toast.LENGTH_LONG).show();
                    }
                }
            });
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