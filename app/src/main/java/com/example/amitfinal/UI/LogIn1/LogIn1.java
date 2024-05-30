package com.example.amitfinal.UI.LogIn1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amitfinal.DB.FirebaseHelper;
import com.example.amitfinal.R;
import com.example.amitfinal.Repository.Repository;
import com.example.amitfinal.UI.HomePage.HomePage;
import com.example.amitfinal.UI.MainActivity.MainActivity;
import com.example.amitfinal.Repository.Moudle.User;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn1 extends AppCompatActivity implements View.OnClickListener
{
    private FirebaseAuth mAuth;
    private FirebaseHelper firebaseHelper;
    private String email,password;
    private EditText inputEmail,inputPassword;
    private Button bt_sign_in;
     private Button btnregister;
    private LogIn1module logIn1module;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in1);
        logIn1module=new LogIn1module(this);
        mAuth = FirebaseAuth.getInstance();
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        bt_sign_in = findViewById(R.id.bt_sign_in);
       btnregister=findViewById(R.id.btnregister);
        bt_sign_in.setOnClickListener(this);
           btnregister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {

        if(bt_sign_in==view)
        {

            String input1= String.valueOf(inputEmail.getText());
            String input2= String.valueOf(inputPassword.getText());
            if(input1.isEmpty()||input2.isEmpty())
            {
                Toast.makeText(this, "Eror", Toast.LENGTH_SHORT).show();
                return;
            }
            // יצירת ProgressDialog
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Signing in...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            User user=new User(inputEmail.getText().toString().trim(),inputPassword.getText().toString().trim());
            logIn1module.signIn(user, new FirebaseHelper.Completed()
            {
                @Override
                public void onComplete(boolean flag) {
                    progressDialog.dismiss();
                    if (flag)
                    {
                        success();
                    }
                    else
                    {
                        Toast.makeText(LogIn1.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        }

if(btnregister==view){
    Intent intent=new Intent(LogIn1.this, MainActivity.class);
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
                Intent intent = new Intent(LogIn1.this,HomePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        builder.show();
    }

}

