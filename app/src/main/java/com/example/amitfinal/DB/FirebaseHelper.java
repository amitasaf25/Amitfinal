package com.example.amitfinal.DB;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.amitfinal.UI.HomePage.HomePage;
import com.example.amitfinal.UI.LogIn1.LogIn1;
import com.example.amitfinal.UI.MainActivity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FirebaseHelper
{
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    public FirebaseHelper(Context context)
    {
        this.context = context;
        this.mAuth=FirebaseAuth.getInstance();
        this.db=FirebaseFirestore.getInstance();

    }
    public interface Completed
    {
        void onComplete(boolean flag);
    }
public interface Completed2
{
        void onComplete(String money);
}

    // פעולה לקבלת כסף מה- Firestore ועדכון סכום הכסף במסד הנתונים
    public void GetMoney(String email,double money,Completed callback)
    {
        Map <String, Object> user =new HashMap<>();
        user.put("money", money);
        db.collection(email).document("money").set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                callback.onComplete(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onComplete(false);
            }
        });
    }

    //פעולה להתחברות משתמש קיים
    public void signIn(String email, String password,Completed callback){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                            FirebaseUser user = mAuth.getCurrentUser();
                            callback.onComplete(true);

                        }
                        else
                        {
                            callback.onComplete(false);
                        }
                    }
                });

    }
    // פעולה ליצירת משתמש חדש
    public void signUp(String email, String password,String username, Completed callback)
    {


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest =new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                    user.updateProfile(profileChangeRequest);
                    callback.onComplete(true);
                }
                else
                {
                    callback.onComplete(false);
                }


    }
        });
   }
    // פעולה לעדכון סכום הכסף במסד הנתונים

    public void UpdateMoney(String email,Completed callback){
       showMoney(email, new Completed2() {
           @Override
           public void onComplete(String money) {
               double money2= Double.parseDouble(money);
               DocumentReference washingtonRef = db.collection(email).document("money");
               washingtonRef
                        .update("money", money2+=5)
                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               callback.onComplete(true);
                           }
                       })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               callback.onComplete(false);
                           }
                       });
           }
       });

   }


    // פעולה להצגת כמות הכסף הנוכחית ממסד הנתונים
   public void showMoney(String email, Completed2 callback)
   {
       FirebaseFirestore db = FirebaseFirestore.getInstance();
       db.collection(email)
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               callback.onComplete(document.getData().get("money").toString().trim());
                           }
                       } else {
                           callback.onComplete("Error 404");
                       }
                   }
               });
   }
    public void deleteAccount(FirebaseUser user, Completed callback)
    {
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {

                        if (task.isSuccessful())
                        {

                            callback.onComplete(true);
                        }
                        else
                        {
                            callback.onComplete(false);
                        }
                    }
                });
    }
    public  void reset(FirebaseUser user,String password,Completed callback)
    {

        user.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            callback.onComplete(true);
                        }
                        else
                        {
                            callback.onComplete(false);
                        }
                    }
                });
    }
    public void deleteUserDocument(String email,Completed callback)
    {
        db.collection(email).document("money")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid) {
                      callback.onComplete(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      callback.onComplete(false);
                    }
                });
    }



}

