package com.example.amitfinal.UI.LogIn1;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn1module {

    private Context context;
    public LogIn1module(Context c)
    {
        this.context = c;
    }

    public boolean CheckErors(EditText et1,EditText et2)
    {


        String strEmail,strPassword;
        strEmail=et1.getText().toString().trim();
        strPassword=et2.getText().toString().trim();
        if(strEmail.isEmpty() )
        {
            Toast.makeText(context, "email problem", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(strPassword.isEmpty() || strPassword.length()<8)
        {
            Toast.makeText(context, "password problem", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

}
