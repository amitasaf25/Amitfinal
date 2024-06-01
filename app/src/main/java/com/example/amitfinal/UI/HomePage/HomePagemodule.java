package com.example.amitfinal.UI.HomePage;

import android.content.Context;
import android.widget.TextView;

import com.example.amitfinal.DB.FirebaseHelper;
import com.example.amitfinal.Repository.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePagemodule
{
    // משתנה לאחסון אובייקט מסוג Repository
    Repository repository;

    // משתנה לאחסון ההקשר (context)
    private Context context;

    // בנאי המאתחל את ההקשר ויוצר אובייקט Repository
    public HomePagemodule(Context context)
    {
        this.context = context;
        repository = new Repository(context);
    }

    // פונקציה להתחלת הפעולה, בודקת אם המשתמש מחובר ומציגה את שמו וכמות הכסף
    public void start(FirebaseUser user, FirebaseAuth mAuth, TextView tvname, TextView tvmoney)
    {
        user = mAuth.getCurrentUser(); // קבלת המשתמש הנוכחי
        if (user != null)
        {
            tvname.setText(user.getDisplayName()); // הצגת שם המשתמש
            repository.showMoney(user.getEmail(), new FirebaseHelper.Completed2() {
                @Override
                public void onComplete(String money)
                {
                    tvmoney.setText(money); // הצגת סכום הכסף הנוכחי
                }
            });
        }
    }

    // פונקציה לבדוק אם מפתח מסוים קיים ב-SharedPreferences
    public boolean hasKey(String strdes1) {
        return repository.hasKey(strdes1);
    }

    // פונקציה לעדכון סכום הכסף למשתמש
    public void UpdateMoney(String email, FirebaseHelper.Completed errorUpdatingMoney) {
        repository.UpdateMoney(email, errorUpdatingMoney);
    }

    // פונקציה להצגת סכום הכסף למשתמש
    public void showMoney(String email, FirebaseHelper.Completed2 completed2) {
        repository.showMoney(email, completed2);
    }

    // פונקציה ליצירת רשומה ב-SharedPreferences
    public void CreateShredPre(String strdes, String fill) {
        repository.CreateShredPre(strdes, fill);
    }

    // פונקציה לניתוק המשתמש מהמערכת
    public void LogOut() {
        repository.LogOut();
    }
}
