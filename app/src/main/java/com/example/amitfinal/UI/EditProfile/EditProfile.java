package com.example.amitfinal.UI.EditProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amitfinal.DB.FirebaseHelper;
import com.example.amitfinal.R;
import com.example.amitfinal.Repository.Repository;
import com.example.amitfinal.UI.HomePage.HomePage;
import com.example.amitfinal.UI.HomePage.HomePagemodule;
import com.example.amitfinal.UI.LogIn1.LogIn1;
import com.example.amitfinal.UI.MainActivity.MainActivity;
import com.example.amitfinal.UI.ProfileHistory.ProfileHistory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditProfile extends AppCompatActivity implements View.OnClickListener
{
   private EditText password;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
  private   Button reset;
  private  Button delete;
private EditProfileMoudle editProfileMoudle;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        editProfileMoudle = new EditProfileMoudle(this);
        password=findViewById(R.id.password);
        reset=findViewById(R.id.reset);
        delete=findViewById(R.id.delete);
        reset.setOnClickListener(this);
        delete.setOnClickListener(this);


    }

//    @Override
//    protected void onStart()
//    {
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        super.onStart();
//    }

    @Override
    public void onClick(View view)
    {

      if(reset==view)
      {
        if (user!=null)
        {
            editProfileMoudle.reset(user, password.getText().toString().trim(), new FirebaseHelper.Completed()
            {
                @Override
                public void onComplete(boolean flag)
                {
                  if(flag)
                  {
                      Toast.makeText(EditProfile.this, "Your password has been reset", Toast.LENGTH_SHORT).show();
                  }
                  else
                  {
                      Toast.makeText(EditProfile.this, "failed1", Toast.LENGTH_SHORT).show();
                  }
                }
            });
        }
        else
        {
            Toast.makeText(EditProfile.this, "failed2", Toast.LENGTH_SHORT).show();
        }

      }
      if(delete==view)
      {
          if (user!=null)
          {

              editProfileMoudle.deleteAccount(user, new FirebaseHelper.Completed()
              {

                  @Override
                  public void onComplete(boolean flag)
                  {
                   if(flag)
                   {
                       editProfileMoudle.deleteUserDocument(user.getEmail(), new FirebaseHelper.Completed() {
                           @Override
                           public void onComplete(boolean flag)
                           {
                             if (flag)
                             {
                                 Toast.makeText(EditProfile.this, "Bye Bye", Toast.LENGTH_SHORT).show();
                                 Intent intent=new Intent(EditProfile.this, MainActivity.class);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                 startActivity(intent);
                             }
                             else
                             {
                                 Toast.makeText(EditProfile.this, "failed1", Toast.LENGTH_SHORT).show();
                             }
                           }
                       });
                   }
                   else
                   {
                       Toast.makeText(EditProfile.this, "failed2", Toast.LENGTH_SHORT).show();
                   }
                  }
              });

          }
          else
          {
              Toast.makeText(EditProfile.this, "failed", Toast.LENGTH_SHORT).show();
          }
      }







    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.home)
        {
            Intent intent=new Intent(EditProfile.this, HomePage.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.profile2)
        {
            Toast.makeText(this, "You already in profile", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.profile)
        {
            Intent intent1=new Intent(EditProfile.this,ProfileHistory.class);
            startActivity(intent1);
            return true;
        } else if (id == R.id.logout)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to log out ,it will clear your history");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    editProfileMoudle.LogOut();
                    Intent intent1 = new Intent(EditProfile.this, LogIn1.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss(); // Dismiss the dialog if canceled
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
