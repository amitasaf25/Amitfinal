package com.example.amitfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private String email,password;
    private EditText inputEmail,inputPassword;
    private Button bt_register,btnlogin;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        bt_register = findViewById(R.id.bt_register);
       btnlogin=findViewById(R.id.btnlogin);
        bt_register.setOnClickListener(this);
        btnlogin.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {





        if(bt_register == v)
        {
            email = inputEmail.getText().toString().trim();
           password = inputPassword.getText().toString().trim();
            if(email.isEmpty() )
            {
                Toast.makeText(this, "email problem", Toast.LENGTH_SHORT).show();
                return;
            }
            if(password.isEmpty() || password.length()<8)
            {
                Toast.makeText(this, "password problem", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password)

                   .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                               FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "good", Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                   });
        }
        if(btnlogin==v)
        {
            Intent intent=new Intent(MainActivity.this,LogIn1.class);
            startActivity(intent);



        }



    }

}

















