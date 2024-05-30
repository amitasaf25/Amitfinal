package com.example.amitfinal.UI.HomePage;

import android.content.Context;
import android.widget.TextView;

import com.example.amitfinal.DB.FirebaseHelper;
import com.example.amitfinal.Repository.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePagemodule

{
    Repository repository;
    public HomePagemodule(Context context){
        repository = new Repository(context);

    }
    public void start(FirebaseUser user, FirebaseAuth mAuth, TextView tvname, TextView tvmoney){
        user = mAuth.getCurrentUser();
        if (user != null)
        {
            tvname.setText(user.getDisplayName());
            repository.showMoney(user.getEmail(), new FirebaseHelper.Completed2() {
                @Override
                public void onComplete(String money)
                {
                    tvmoney.setText(money);
                }
            });
        }
    }


    public boolean hasKey(String strdes1) {
        return repository.hasKey(strdes1);
    }

    public void UpdateMoney(String email, FirebaseHelper.Completed errorUpdatingMoney) {
        repository.UpdateMoney(email,errorUpdatingMoney);
    }

    public void showMoney(String email, FirebaseHelper.Completed2 completed2) {
        repository.showMoney(email, completed2);
    }

    public void CreateShredPre(String strdes, String fill) {
        repository.CreateShredPre(strdes,fill);
    }

    public void LogOut() {
        repository.LogOut();
    }
}
