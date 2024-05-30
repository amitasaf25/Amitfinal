package com.example.amitfinal.UI.LogIn1;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amitfinal.DB.FirebaseHelper;
import com.example.amitfinal.Repository.Moudle.User;
import com.example.amitfinal.Repository.Repository;

public class LogIn1module {

    private Context context;
    Repository repository;
    public LogIn1module(Context c)
    {
        this.context = c;
        repository=new Repository(c);
    }


    public void signIn(User user, FirebaseHelper.Completed failed)
    {
        repository.signIn(user,failed);
    }
}
