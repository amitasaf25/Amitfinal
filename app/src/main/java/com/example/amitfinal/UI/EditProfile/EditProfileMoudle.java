package com.example.amitfinal.UI.EditProfile;

import android.content.Context;

import com.example.amitfinal.DB.FirebaseHelper;
import com.example.amitfinal.Repository.Repository;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileMoudle
{
  private    Repository repository;
 private     Context c;

    public EditProfileMoudle(Repository repository, Context c)
    {
        this.c = c;
        repository=new Repository(c);

    }


    public void reset(FirebaseUser user, String trim, FirebaseHelper.Completed completed)
    {
        repository.reset(user,trim,completed);
    }

    public void deleteAccount(FirebaseUser user, FirebaseHelper.Completed completed)
    {
        repository.deleteAccount(user,completed);
    }

    public void deleteUserDocument(String email, FirebaseHelper.Completed completed)
    {
        repository.deleteUserDocument(email,completed);

    }

    public void LogOut()
    {
        repository.LogOut();
    }
}
