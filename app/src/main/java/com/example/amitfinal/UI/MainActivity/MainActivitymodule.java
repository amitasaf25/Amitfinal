package com.example.amitfinal.UI.MainActivity;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amitfinal.DB.FirebaseHelper;
import com.example.amitfinal.Repository.Moudle.User;
import com.example.amitfinal.Repository.Repository;

public class MainActivitymodule
{
    Context context;
    Repository repository;

    public MainActivitymodule(Context c)
    {
        this.context= c;
        repository=new Repository(c);
    }


    public void signUp(User user, FirebaseHelper.Completed completed)
    {
        repository.signUp(user,completed);
    }

    public void getMoney(String string, int i, FirebaseHelper.Completed failed)
    {
        repository.getMoney(string,i,failed);
    }

}
