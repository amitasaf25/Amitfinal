package com.example.amitfinal.UI.LogIn1;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amitfinal.DB.FirebaseHelper;
import com.example.amitfinal.Repository.Moudle.User;
import com.example.amitfinal.Repository.Repository;

public class LogIn1module
{
    // משתנה לאחסון ההקשר
    private Context context;

    // משתנה לאחסון המחסן (repository)
    Repository repository;

    // בנאי שמקבל הקשר ומאתחל את המחסן
    public LogIn1module(Context c)
    {
        this.context = c;
        repository = new Repository(c);
    }

    // פונקציה להתחברות של משתמש
    public void signIn(User user, FirebaseHelper.Completed failed)
    {
        // קריאה לפונקציית ההתחברות במחסן
        repository.signIn(user, failed);
    }
}

