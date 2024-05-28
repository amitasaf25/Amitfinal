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
import com.example.amitfinal.ProfileHistory;
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
import java.util.Calendar;

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

        if (id == R.id.home)
        {
            Toast.makeText(this, "you already in home", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.profile)
        {
            Intent intent=new Intent(HomePage.this, ProfileHistory.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent1=new Intent(HomePage.this, LogIn1.class);
            startActivity(intent1);
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
        etdes=findViewById(R.id.etdes);
        tvdes=findViewById(R.id.tvdes);
        btncamera.setOnClickListener(this);
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
        
        if (btndes==view)
        {


           if (user!= null)
           {
               String strdes1=String.valueOf(etdes.getText());
               if(!strdes1.isEmpty())
               {
                   if (repository.hasKey(strdes1))
                   {
                       Toast.makeText(this, "you already have this name of item", Toast.LENGTH_SHORT).show();
                       return;
                   }
               }
               else{
                   Toast.makeText(this, "eror", Toast.LENGTH_SHORT).show();
                   return;
               }
               repository.UpdateMoney(user.getEmail(), new FirebaseHelper.Completed() {
                   @Override
                   public void onComplete(boolean flag)
                   {
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
                           repository.CreateShredPre(strdes,fill());
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
    public String fill()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Months are zero-based
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String currentTimeAndDate = "time and date of report: " + hour + ":" + minute + ":" + second + " " + month + "/" + dayOfMonth + "/" + year;

        return currentTimeAndDate;
    }
}