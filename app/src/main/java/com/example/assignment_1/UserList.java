package com.example.assignment_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.assignment_1.OrderSchema.*;
import java.util.ArrayList;
import java.util.List;

public class UserList
{
    private List<User> userList = new ArrayList<>();
    private SQLiteDatabase db;

    public UserList() {}

    public int size()
    {
        return userList.size();
    }

    public void load(Context context)
    {
        this.db = new OrderDbHelper(context.getApplicationContext()).getWritableDatabase();

        MyCursor cursor = new MyCursor( db.query( UserTable.NAME,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null) );
        try
        {
            cursor.moveToFirst();
            while( !cursor.isAfterLast() )
            {
                userList.add( cursor.getUser() );
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }
    }

    public void add( User value )
    {
        ContentValues cv = new ContentValues();
        cv.put( UserTable.Cols.EMAIL, value.getEmail() );
        cv.put( UserTable.Cols.PWD, value.getPwd() );
        cv.put( UserTable.Cols.USER_ID, value.getId() );
        db.insert(UserTable.NAME, null, cv);

        userList.add( new User( value.getEmail(), value.getPwd(), value.getId() ) );
    }

    public int newUserID()
    {
        MyCursor cursor = new MyCursor( db.query( UserTable.NAME,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null) );
        return cursor.getCount() + 1;
    }

    public User find(String email)
    {
        int ii = 0;
        boolean found = false;
        User user = null;

        if(userList.size() > 0)
        {
            do
            {
                user = userList.get(ii);

                if( user.getEmail().equals(email) )
                {
                    found = true;
                }

                ii++;

            }while( ii < userList.size() && !found );

            if(!found)
            {
                user = null;
            }
        }


        return user;
    }
}
