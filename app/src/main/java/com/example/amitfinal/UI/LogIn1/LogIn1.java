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
    // משתנה לאחסון FirebaseAuth
    private FirebaseAuth mAuth;


    // משתנים לאחסון אימייל וסיסמה
    private String email, password;

    // משתנים לאחסון שדות טקסט לאימייל ולסיסמה
    private EditText inputEmail, inputPassword;

    // משתנים לאחסון כפתורים
    private Button bt_sign_in;
    private Button btnregister;

    // משתנה לאחסון מודול התחברות
    private LogIn1module logIn1module;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // הגדרת תצוגת הפעילות
        setContentView(R.layout.activity_log_in1);

        // יצירת אובייקט של LogIn1module
        logIn1module = new LogIn1module(this);

        // אתחול FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // אתחול שדות טקסט לאימייל ולסיסמה
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        // אתחול כפתורים
        bt_sign_in = findViewById(R.id.bt_sign_in);
        btnregister = findViewById(R.id.btnregister);

        // הגדרת מאזינים לכפתורים
        bt_sign_in.setOnClickListener(this);
        btnregister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        // בדיקה אם כפתור ההתחברות נלחץ
        if (bt_sign_in == view)
        {
            String input1 = String.valueOf(inputEmail.getText());
            String input2 = String.valueOf(inputPassword.getText());

            // בדיקה אם אחד משדות הטקסט ריק
            if (input1.isEmpty() || input2.isEmpty())
            {
                Toast.makeText(this, "Email or password are empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // יצירת ProgressDialog
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Signing in...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            // יצירת אובייקט משתמש חדש
            User user = new User(inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim());

            // קריאה לפונקציית ההתחברות במודול
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
                        Toast.makeText(LogIn1.this, "failed, can't find user like this", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // בדיקה אם כפתור ההרשמה נלחץ
        if (btnregister == view) {
            Intent intent = new Intent(LogIn1.this, MainActivity.class);
            startActivity(intent);
        }
    }

    // פונקציה המופעלת בעת הצלחת ההתחברות
    public void success()
    {
        Dialog builder = new Dialog(this);
        builder.setCancelable(false);
        builder.setContentView(R.layout.layoutdialog);

        // הגדרת פרמטרים של חלון הדיאלוג
        WindowManager.LayoutParams lb = new WindowManager.LayoutParams();
        lb.copyFrom(builder.getWindow().getAttributes());
        lb.width = WindowManager.LayoutParams.MATCH_PARENT;
        lb.height = WindowManager.LayoutParams.WRAP_CONTENT;
        builder.getWindow().setAttributes(lb);

        // אתחול כפתור סיום בדיאלוג
        Button done = builder.findViewById(R.id.done);

        // הגדרת מאזין לכפתור הסיום
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                builder.dismiss();
                Intent intent = new Intent(LogIn1.this, HomePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        builder.show();
    }
}

