package com.example.amitfinal.UI.MainActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amitfinal.FirebaseHelper;
import com.example.amitfinal.R;
import com.example.amitfinal.Repository.Repository;
import com.example.amitfinal.UI.HomePage.HomePage;
import com.example.amitfinal.UI.LogIn1.LogIn1;
import com.example.amitfinal.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private FirebaseAuth mAuth;
    private FirebaseHelper firebaseHelper;
    private Repository repository;
    private String email,password;
    private EditText inputEmail,inputPassword,inputUsername;
    private Button bt_register,btnlogin;


private MainActivitymodule ma1;

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
           Intent intent=new Intent(MainActivity.this, HomePage.class);
           startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        inputUsername=findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        bt_register = findViewById(R.id.bt_register);
        btnlogin=findViewById(R.id.btnlogin);
        firebaseHelper=new FirebaseHelper(this);
        repository=new Repository(this,firebaseHelper);
        bt_register.setOnClickListener(this);
        btnlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(bt_register == v)
        {
            String input= String.valueOf(inputUsername.getText());
            String input2= String.valueOf(inputEmail.getText());
            String input3= String.valueOf(inputPassword.getText());
            if(input.isEmpty()||input2.isEmpty()||input3.isEmpty())
            {
                Toast.makeText(this, "Eror", Toast.LENGTH_SHORT).show();
                return;
            }
            User user=new User(inputEmail.getText().toString().trim(),inputPassword.getText().toString().trim(),inputUsername.getText().toString());
            repository.signUp(user, new FirebaseHelper.Completed()
            {
                @Override
                public void onComplete(boolean flag)
                {
                    if(flag)
                    {
                        repository.getMoney(inputEmail.getText().toString(), 0, new FirebaseHelper.Completed() {
                            @Override
                            public void onComplete(boolean flag)
                            {
                                if(flag)
                                {
                                 success();
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                }

            });


        }
        if(btnlogin==v)
        {
            Intent intent=new Intent(MainActivity.this, LogIn1.class);
            startActivity(intent);



        }




    }
    public void success()
    {
        Dialog builder = new Dialog(this);
        builder.setCancelable(false);
        builder.setContentView(R.layout.layoutdialog);
        WindowManager.LayoutParams lb = new WindowManager.LayoutParams();
        lb.copyFrom(builder.getWindow().getAttributes());
        lb.width = WindowManager.LayoutParams.MATCH_PARENT;
        lb.height = WindowManager.LayoutParams.WRAP_CONTENT;
        builder.getWindow().setAttributes(lb);
        Button done = builder.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                builder.dismiss();
                Intent intent = new Intent(MainActivity.this,HomePage.class);
                startActivity(intent);
            }
        });

        builder.show();
    }

}

















