package com.example.amitfinal.UI.MainActivity;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivitymodule
{
    Context context;

    public MainActivitymodule(Context c)
    {
        this.context= c;

    }


    public boolean CheckErors(EditText et1,EditText et2)
    {
        String email = et1.getText().toString().trim();
        String password = et2.getText().toString().trim();
        if(email.isEmpty() )
        {
            Toast.makeText(context, "email problem", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.isEmpty() || password.length()<8)
        {
            Toast.makeText(context, "password problem", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}
