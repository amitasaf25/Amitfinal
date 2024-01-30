package com.example.amitfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn1 extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private String email,password;
    private EditText inputEmail,inputPassword;
    private Button bt_sign_in;
    Button btnregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in1);
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

           String strEmail,strPassword;
           strEmail=inputEmail.getText().toString().trim();
           strPassword=inputPassword.getText().toString().trim();
            if(strEmail.isEmpty() )
            {
                Toast.makeText(this, "email problem", Toast.LENGTH_SHORT).show();
                return;
            }
            if(strPassword.isEmpty() || strPassword.length()<8)
            {
                Toast.makeText(this, "password problem", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LogIn1.this, "succses", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LogIn1.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });




        }

if(btnregister==view){
    Intent intent=new Intent(LogIn1.this,MainActivity.class);
    startActivity(intent);
}



    }

}

