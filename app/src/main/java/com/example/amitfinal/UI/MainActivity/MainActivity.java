package com.example.amitfinal.UI.MainActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amitfinal.DB.FirebaseHelper;
import com.example.amitfinal.R;
import com.example.amitfinal.Repository.Repository;
import com.example.amitfinal.UI.HomePage.HomePage;
import com.example.amitfinal.UI.LogIn1.LogIn1;
import com.example.amitfinal.Repository.Moudle.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    // משתנים עבור Firebase
    private FirebaseAuth mAuth;
    private FirebaseHelper firebaseHelper;

    // משתנים עבור פרטי המשתמש
    private String email, password;
    private EditText inputEmail, inputPassword, inputUsername;
    private Button bt_register, btnlogin;

    // מודול עבור MainActivity
    private MainActivitymodule mainActivitymodule;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // אתחול המודול MainActivity
        mainActivitymodule = new MainActivitymodule(this);

        // קבלת התוכנית הנוכחית של Firebase
        mAuth = FirebaseAuth.getInstance();

        // קבלת כתובות האימייל והסיסמה מה-EditText
        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        // קבלת הכפתורים מה-XML
        bt_register = findViewById(R.id.bt_register);
        btnlogin = findViewById(R.id.btnlogin);

        // הוספת מאזין לאירועי לחיצה על הכפתורים
        bt_register.setOnClickListener(this);
        btnlogin.setOnClickListener(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // בדיקה אם המשתמש כבר מחובר, אם כן, הוא יועבר לדף הבית
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v)
    {
        if (bt_register == v)
        {
            // בדיקת תקינות קלטי המשתמש
            String input = String.valueOf(inputUsername.getText());
            String input2 = String.valueOf(inputEmail.getText());
            String input3 = String.valueOf(inputPassword.getText());
            if (input.isEmpty() || input2.isEmpty() || input3.isEmpty())
            {
                Toast.makeText(this, "Username, email, or password is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // יצירת משתמש חדש
            User user = new User(inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim(), inputUsername.getText().toString());

            // הרשמה לאפליקציה וקבלת המטבעות הראשוניים
            mainActivitymodule.signUp(user, new FirebaseHelper.Completed()
            {
                @Override
                public void onComplete(boolean flag)
                {
                    if (flag)
                    {
                        // בדיקה אם ההרשמה הצליחה, ואם כן, העברה לדף הבית
                        mainActivitymodule.getMoney(inputEmail.getText().toString(), 0, new FirebaseHelper.Completed() {
                            @Override
                            public void onComplete(boolean flag)
                            {
                                if (flag)
                                {
                                    success();
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Failed, email or password is wrong", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
        else if (btnlogin == v)
        {
            // העברה לדף ההתחברות
            Intent intent = new Intent(MainActivity.this, LogIn1.class);
            startActivity(intent);
        }
    }

    // פעולה להצגת הודעת הצלחה
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

















