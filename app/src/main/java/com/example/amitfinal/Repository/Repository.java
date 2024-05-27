package com.example.amitfinal.Repository;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.amitfinal.FirebaseHelper;
import com.example.amitfinal.LogShredPre;
import com.example.amitfinal.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Repository
{
    private Context context;
    private  SharedPreferences sharedPreferences;
    private FirebaseHelper firebaseHelper;


    public Repository(Context context)
    {
        this.context = context;
    }

    public Repository(Context context, FirebaseHelper firebaseHelper)
    {
        this.context = context;
        this.firebaseHelper = firebaseHelper;
    }

public void signIn(User user, FirebaseHelper.Completed callback )
{
    firebaseHelper=new FirebaseHelper(context);
    firebaseHelper.signIn(user.getEmail(), user.getPassword(), new FirebaseHelper.Completed() {
        @Override
        public void onComplete(boolean flag)
        {
            callback.onComplete(flag);


        }
    });
}

    public void signUp(User user, FirebaseHelper.Completed callback )
    {
        firebaseHelper=new FirebaseHelper(context);
        firebaseHelper.signUp(user.getEmail(), user.getPassword(),user.getName(), new FirebaseHelper.Completed() {
            @Override
            public void onComplete(boolean flag)
            {
                callback.onComplete(flag);
                if(flag)
                {

                    SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    

                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("email",user.getEmail());
                    myEdit.putString("pass", user.getPassword());
                    myEdit.commit();
                }

            }
        });
    }
    public void getMoney(String email,double money,FirebaseHelper.Completed callback)
    {
     firebaseHelper=new FirebaseHelper(context);
     firebaseHelper.GetMoney(email, money, new FirebaseHelper.Completed()
     {
         @Override
         public void onComplete(boolean flag)
         {
             callback.onComplete(flag);
         }
     });

    }
    public void UpdateMoney(String email, FirebaseHelper.Completed callback){
        firebaseHelper=new FirebaseHelper(context);
        firebaseHelper.UpdateMoney(email, new FirebaseHelper.Completed() {
            @Override
            public void onComplete(boolean flag) {
                callback.onComplete(flag);
            }
        });
    }
    public void showMoney(String email, FirebaseHelper.Completed2 callback){
        firebaseHelper=new FirebaseHelper(context);
        firebaseHelper.showMoney(email, new FirebaseHelper.Completed2()
        {
            @Override
            public void onComplete(String money) {
                callback.onComplete(money);
            }
        });

    }
public void CreateShredPre(String descrip,String date)
{
        sharedPreferences=context.getSharedPreferences("sharedpre",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(descrip, date);
        editor.commit();
}
public List<LogShredPre> getShredpre()
{
List<LogShredPre> list=new LinkedList<>();

sharedPreferences=context.getSharedPreferences("sharedpre",MODE_APPEND);
    Map<String, ?> allEntries = sharedPreferences.getAll();
    for (Map.Entry<String, ?> entry : allEntries.entrySet())
    {
        LogShredPre log=new LogShredPre(entry.getKey().toString(),entry.getValue().toString());
        list.add(log);
    }
    return list;
}
    public boolean hasKey(String key) {
        return sharedPreferences.contains(key);
    }



}
