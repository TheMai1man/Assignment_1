package com.example.assignment_1;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment
{
    private CommonData mViewModel;
    private UserList data;

    private EditText address, pwd;
    private Button login, register;

    public LoginFragment() {}

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        data = new UserList();
        data.load(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_login, ui, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);

        address = (EditText) view.findViewById(R.id.usernameEdit);
        pwd = (EditText) view.findViewById(R.id.pwdEdit);
        login = (Button) view.findViewById(R.id.loginButton);
        register = (Button) view.findViewById(R.id.registerButton);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //validate email
                if( emailValid() && emailExists() )
                {
                    User user = data.find(address.getText().toString());
                    //validate pwd
                    if( pwdValid() && pwd.getText().toString().equals(user.getPwd()) )
                    {
                        //set as current user
                        mViewModel.setLoggedInUser(user);
                        mViewModel.setCheckoutConfirm(true);
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
                            int i = data.newUserID();
                            User user = new User( address.getText().toString(), pwd.getText().toString(), i );
                            data.add(user);
                            //setUser
                            mViewModel.setLoggedInUser(user);
                            mViewModel.setCheckoutConfirm(true);
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
        if( data.size() > 0 && data.find( address.getText().toString() ) != null )
        {
            exists = true;
        }

        return exists;
    }

}