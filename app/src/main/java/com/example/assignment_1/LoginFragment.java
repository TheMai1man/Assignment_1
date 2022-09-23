package com.example.assignment_1;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment
{
    private CommonData mViewModel;
    private SQLiteDatabase db;

    private EditText address, pwd;
    private Button login, register;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login, ui, false);

        address = (EditText) view.findViewById(R.id.usernameEdit);
        pwd = (EditText) view.findViewById(R.id.pwdEdit);
        login = (Button) view.findViewById(R.id.loginButton);
        register = (Button) view.findViewById(R.id.registerButton);

        this.db = new OrderDbHelper(getContext()).getWritableDatabase();

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //validate email
                if( emailValid() && emailExists() )
                {
                    User user = mViewModel.getUserList().find(address.getText().toString());
                    //validate pwd
                    if( pwdValid() && pwd.getText().toString().equals(user.getPwd()) )
                    {
                        //set as current user
                        mViewModel.setLoggedInUser(user);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Password is incorrect!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "No account with this email!", Toast.LENGTH_LONG).show();
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //validate email
                if( emailValid() )
                {
                    //validate password
                    if( pwdValid() )
                    {
                        if( !emailExists() )
                        {
                            //create user
                            int i = mViewModel.getUserList().newUserID();
                            User user = new User( address.getText().toString(), pwd.getText().toString(), i );
                            mViewModel.getUserList().add(user);
                            //setUser
                            mViewModel.setLoggedInUser(user);
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Email already in use by another account!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Password must be at least 8 characters!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Email invalid!", Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }

    public boolean pwdValid()
    {
        boolean valid = false;
        String pass;
        if( !pwd.getText().toString().equals("") )
        {
            pass = pwd.getText().toString();
            if(pass.length() > 7)
            {
                valid = true;
            }
        }

        return valid;
    }

    public boolean emailValid()
    {
        boolean valid = false;
        String email;
        if( !address.getText().toString().equals("") )
        {
            email = address.getText().toString();
            if ( email.matches( "^(.+)@(.+)$" ) )
            {
                valid = true;
            }
        }

        return valid;
    }

    public boolean emailExists()
    {
        boolean exists = false;
        if( mViewModel.getUserList().find( address.getText().toString() ) != null )
        {
            exists = true;
        }

        return exists;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);
    }
}