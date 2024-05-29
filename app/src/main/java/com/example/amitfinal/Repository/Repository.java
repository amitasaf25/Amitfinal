package com.example.amitfinal.Repository;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.amitfinal.DB.FirebaseHelper;
import com.example.amitfinal.LogShredPre;
import com.example.amitfinal.Repository.Moudle.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository
{
    // משתנים פרטיים לאחסון הקשר, העדפות משותפות ועוזר Firebase
    private Context context;
    private SharedPreferences sharedPreferences;
    private FirebaseHelper firebaseHelper;

    // בנאי ראשוני המאתחל את הקשר ועוזר Firebase
    public Repository(Context context)
    {
        this.context = context;
        this.firebaseHelper = new FirebaseHelper(context);
    }
    public Repository()
    {

    }

    // פונקציה להתחברות של משתמש
    public void signIn(User user, FirebaseHelper.Completed callback) {
        firebaseHelper = new FirebaseHelper(context);
        firebaseHelper.signIn(user.getEmail(), user.getPassword(), new FirebaseHelper.Completed() {
            @Override
            public void onComplete(boolean flag) {
                callback.onComplete(flag); // החזרת תוצאת ההתחברות
            }
        });
    }

    // פונקציה לרישום משתמש חדש
    public void signUp(User user, FirebaseHelper.Completed callback) {
        firebaseHelper = new FirebaseHelper(context);
        firebaseHelper.signUp(user.getEmail(), user.getPassword(), user.getName(), new FirebaseHelper.Completed() {
            @Override
            public void onComplete(boolean flag) {
                callback.onComplete(flag); // החזרת תוצאת הרישום
                if (flag) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("email", user.getEmail());
                    myEdit.putString("pass", user.getPassword());
                    myEdit.commit(); // שמירת פרטי המשתמש בהעדפות משותפות
                }
            }
        });
    }

    // פונקציה להוספת כסף למשתמש
    public void getMoney(String email, double money, FirebaseHelper.Completed callback) {
        firebaseHelper = new FirebaseHelper(context);
        firebaseHelper.GetMoney(email, money, new FirebaseHelper.Completed() {
            @Override
            public void onComplete(boolean flag) {
                callback.onComplete(flag); // החזרת תוצאת הפעולה
            }
        });
    }

    // פונקציה לעדכון כסף למשתמש
    public void UpdateMoney(String email, FirebaseHelper.Completed callback) {
        firebaseHelper = new FirebaseHelper(context);
        firebaseHelper.UpdateMoney(email, new FirebaseHelper.Completed() {
            @Override
            public void onComplete(boolean flag) {
                callback.onComplete(flag); // החזרת תוצאת הפעולה
            }
        });
    }

    // פונקציה להצגת הסכום הנוכחי של המשתמש
    public void showMoney(String email, FirebaseHelper.Completed2 callback)
    {
        firebaseHelper = new FirebaseHelper(context);
        firebaseHelper.showMoney(email, new FirebaseHelper.Completed2()
        {
            @Override
            public void onComplete(String money)
            {
                callback.onComplete(money); // החזרת הסכום הנוכחי
            }
        });
    }

    // פונקציה ליצירת SharedPreferences עם תיאור ותאריך
    public void CreateShredPre(String descrip, String date)
    {
        sharedPreferences = context.getSharedPreferences("sharedpre", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(descrip, date);
        editor.commit(); // שמירת המידע בהעדפות משותפות
    }

    // פונקציה לקבלת כל הרשומות ב-SharedPreferences
    public List<LogShredPre> getShredpre()
    {
        List<LogShredPre> list = new ArrayList<>();
        sharedPreferences = context.getSharedPreferences("sharedpre", MODE_APPEND);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet())
        {
            LogShredPre log = new LogShredPre(entry.getKey(), entry.getValue().toString());
            list.add(log); // הוספת הרשומה לרשימה
        }
        return list; // החזרת הרשימה
    }

    // פונקציה לבדוק אם string מסוים קיים בSharedPreferences
    public boolean hasKey(String key)
    {
        sharedPreferences = context.getSharedPreferences("sharedpre", MODE_PRIVATE);
        return sharedPreferences.contains(key); // החזרת תוצאת הבדיקה
    }
    //פעולה למחיקת חשבון
    public void deleteAccount(FirebaseUser user,FirebaseHelper.Completed callback)
    {
        firebaseHelper.deleteAccount(user,callback);
    }
    //פעולה לאיפוס סיסמה
    public void reset(FirebaseUser user,String password,FirebaseHelper.Completed callback)
    {
     firebaseHelper.reset(user,password,callback);
    }
    public void deleteUserDocument(String email,FirebaseHelper.Completed callback)
    {
        firebaseHelper.deleteUserDocument(email,callback);
    }
}
