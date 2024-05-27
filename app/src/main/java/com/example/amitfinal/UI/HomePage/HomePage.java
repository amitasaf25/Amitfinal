package com.example.amitfinal.UI.HomePage;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaSession2Service;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amitfinal.FirebaseHelper;
import com.example.amitfinal.R;
import com.example.amitfinal.Repository.Repository;
import com.example.amitfinal.UI.LogIn1.LogIn1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;

public class HomePage extends AppCompatActivity implements View.OnClickListener
{
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

        if (id == R.id.home) {
            Toast.makeText(this, "press home", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.profile) {
            Toast.makeText(this, "press profile", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.logout) {
            Toast.makeText(this, "press logout", Toast.LENGTH_SHORT).show();
            return true;
        } else
        {
            return super.onOptionsItemSelected(item);
          }
        }


    Button btncamera;
    Button btnlogout;
    Button btndes;
    FirebaseAuth mAuth;
    EditText etdes;
    ImageView image1;
    TextView tvname;
    TextView tvmoney;
    TextView tvdes;
    Bitmap photo;
    Repository repository;
    FirebaseHelper firebaseHelper;
    FirebaseUser user;


    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        image1.setVisibility(View.VISIBLE);
                        // There are no request codes
                        photo = (Bitmap) result.getData().getExtras().get("data");
                        image1.setImageBitmap(photo);
                        tvdes.setText("Describe what you recycle");
                        tvdes.setVisibility(View.VISIBLE);
                        etdes.setVisibility(View.VISIBLE);
                        btndes.setVisibility(View.VISIBLE);

                    }
                }
            });


    @Override
    protected void onStart() {
        super.onStart();

        user=mAuth.getCurrentUser();
        if(user!=null){
            tvname.setText(user.getDisplayName());
            firebaseHelper=new FirebaseHelper(this);
            repository=new Repository(this,firebaseHelper);
            repository.showMoney(user.getEmail(), new FirebaseHelper.Completed2() {
                @Override
                public void onComplete(String money) {
                    tvmoney.setText(money);
                }
            });
         }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        image1=findViewById(R.id.image1);
        btncamera=findViewById(R.id.btncamera);
        btndes=findViewById(R.id.btndes);
        tvmoney=findViewById(R.id.tvmoney);
        tvname=findViewById(R.id.tvname);
        btnlogout=findViewById(R.id.btnlogout);
        etdes=findViewById(R.id.etdes);
        tvdes=findViewById(R.id.tvdes);
        btncamera.setOnClickListener(this);
        btnlogout.setOnClickListener(this);
        btndes.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();
        firebaseHelper=new FirebaseHelper(this);
        repository=new Repository(this,firebaseHelper);


    }

    @Override
    public void onClick(View view)
    {
        if(view == btncamera)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraActivityResultLauncher.launch(intent);

        }
        if (view==btnlogout) 
        {
            repository.CreateShredPre("gilad","11/1/2021");
            repository.CreateShredPre("amit","11/2/2021");

            // FirebaseAuth.getInstance().signOut();
           // Intent intent=new Intent(HomePage.this, LogIn1.class);
            //startActivity(intent);
        }
        
        if (btndes==view)
        {


           if (user!= null)
           {
               repository.UpdateMoney(user.getEmail(), new FirebaseHelper.Completed() {
                   @Override
                   public void onComplete(boolean flag) {
                    String strdes =String.valueOf(etdes.getText());
                       if (flag&&!strdes.isEmpty())
                       {
                           tvdes.setVisibility(View.INVISIBLE);
                           btndes.setVisibility(View.INVISIBLE);
                           etdes.setVisibility(View.INVISIBLE);
                           image1.setVisibility(View.INVISIBLE);
                           Toast.makeText(HomePage.this, "you got 5 coins", Toast.LENGTH_SHORT).show();
                           repository.showMoney(user.getEmail(), new FirebaseHelper.Completed2()
                           {
                               @Override
                               public void onComplete(String money)
                               {
                                   tvmoney.setText(money);
                               }
                           });
                       }
                       else
                       {
                           Toast.makeText(HomePage.this, "Eror", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
        }
    }
}