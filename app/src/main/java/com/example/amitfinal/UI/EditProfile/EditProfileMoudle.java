package com.example.amitfinal.UI.EditProfile;

import android.content.Context;

import com.example.amitfinal.DB.FirebaseHelper;
import com.example.amitfinal.Repository.Repository;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileMoudle
{
    // משתנה לאחסון אובייקט מסוג Repository
    private Repository repository;

    // משתנה לאחסון ההקשר (context)
    private Context c;

    // בנאי המאתחל את ההקשר ויוצר אובייקט Repository
    public EditProfileMoudle(Context c)
    {
        this.c = c;
        repository = new Repository(c);
    }

    // פונקציה לאיפוס סיסמה באמצעות הקריאה ל-repository
    public void reset(FirebaseUser user, String trim, FirebaseHelper.Completed completed)
    {
        repository.reset(user, trim, completed);
    }

    // פונקציה למחיקת חשבון משתמש באמצעות הקריאה ל-repository
    public void deleteAccount(FirebaseUser user, FirebaseHelper.Completed completed)
    {
        repository.deleteAccount(user, completed);
    }

    // פונקציה למחיקת מסמך המשתמש במסד הנתונים באמצעות הקריאה ל-repository
    public void deleteUserDocument(String email, FirebaseHelper.Completed completed)
    {
        repository.deleteUserDocument(email, completed);
    }

    // פונקציה לניתוק המשתמש מהמערכת באמצעות הקריאה ל-repository
    public void LogOut()
    {
        repository.LogOut();
    }
}

