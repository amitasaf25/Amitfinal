package com.example.amitfinal.UI.HomePage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amitfinal.DB.FirebaseHelper;
import com.example.amitfinal.UI.EditProfile.EditProfile;
import com.example.amitfinal.UI.ProfileHistory.ProfileHistory;
import com.example.amitfinal.R;
import com.example.amitfinal.Repository.Repository;
import com.example.amitfinal.UI.LogIn1.LogIn1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class HomePage extends AppCompatActivity implements View.OnClickListener
{
    // משתנים עבור רכיבי ממשק המשתמש ואובייקטים חיוניים
    Button btncamera;
    Button btndes;
    FirebaseAuth mAuth;
    EditText etdes;
    ImageView image1;
    TextView tvname;
    TextView tvmoney;
    TextView tvdes;
    Bitmap photo;
//    Repository repository;
    FirebaseUser user;
    HomePagemodule homePagemodule;

    // אתחול הפעילות והמשתנים
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        // אתחול המשתנים
        homePagemodule = new HomePagemodule(this);
        image1 = findViewById(R.id.image1);
        btncamera = findViewById(R.id.btncamera);
        btndes = findViewById(R.id.btndes);
        tvmoney = findViewById(R.id.tvmoney);
        tvname = findViewById(R.id.tvname);
        etdes = findViewById(R.id.etdes);
        tvdes = findViewById(R.id.tvdes);
        btncamera.setOnClickListener(this);
        btndes.setOnClickListener(this);
        homePagemodule.start(user, mAuth, tvname, tvmoney);
    }



    // אתחול ראשוני של המסך
    @Override
    protected void onStart()
    {
        super.onStart();
       // homePagemodule.start(user,mAuth,tvname,tvmoney);

    }


    // פונקציה ליצירת מחרוזת עם תאריך ושעה נוכחיים
    public String fill()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // חודשים מתחילים מ-0
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        return "time and date of report: " + hour + ":" + minute + ":" + second + " " + month + "/" + dayOfMonth + "/" + year;
    }



    // טיפול בלחיצות על כפתורים
    @Override
    public void onClick(View view)
    {
        if (view == btncamera)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraActivityResultLauncher.launch(intent);
        }

        if (view == btndes)
        {
            if (user != null)
            {
                String strdes1 = etdes.getText().toString().trim();
                if (!strdes1.isEmpty())
                {
                    if (homePagemodule.hasKey(strdes1))
                    {
                        Toast.makeText(this, "You already have this name of item", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else
                {
                    Toast.makeText(this, "Error: description is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                homePagemodule.UpdateMoney(user.getEmail(), new FirebaseHelper.Completed() {
                    @Override
                    public void onComplete(boolean flag)
                    {
                        if (flag)
                        {
                            String strdes = etdes.getText().toString().trim();
                            if (!strdes.isEmpty())
                            {
                                tvdes.setVisibility(View.INVISIBLE);
                                btndes.setVisibility(View.INVISIBLE);
                                etdes.setVisibility(View.INVISIBLE);
                                image1.setVisibility(View.INVISIBLE);
                                //סגירת מקלדת
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);


                                Toast.makeText(HomePage.this, "You got 5 coins", Toast.LENGTH_SHORT).show();

                                homePagemodule.showMoney(user.getEmail(), new FirebaseHelper.Completed2()
                                {
                                    @Override
                                    public void onComplete(String money)
                                    {
                                        tvmoney.setText(money);
                                    }
                                });

                                homePagemodule.CreateShredPre(strdes, fill());
                            }
                            else
                            {
                                Toast.makeText(HomePage.this, "Error: description became empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(HomePage.this, "Error updating money", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {
                Toast.makeText(HomePage.this, "Error: user is null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // יצירת תפריט ראשי
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // ניהול בחירת פריט בתפריט
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            Toast.makeText(this, "you already in home", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.profile2) {
            Intent intent = new Intent(HomePage.this, EditProfile.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.profile) {
            Intent intent = new Intent(HomePage.this, ProfileHistory.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.logout)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to log out ,it will clear your history");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    FirebaseAuth.getInstance().signOut();
                    homePagemodule.LogOut();
                    Intent intent1 = new Intent(HomePage.this, LogIn1.class);
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
        } else
        {
            return super.onOptionsItemSelected(item);
        }
    }




    //  פעילות מצלמה
    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        image1.setVisibility(View.VISIBLE);
                        photo = (Bitmap) result.getData().getExtras().get("data");
                        photo = cropToSquare(photo);
                        image1.setImageBitmap(photo);
                        tvdes.setText("Describe what you recycle");
                        tvdes.setVisibility(View.VISIBLE);
                        etdes.setVisibility(View.VISIBLE);
                        etdes.setText("");
                        btndes.setVisibility(View.VISIBLE);
                    }
                }
            });

    //פעולה לחיתוך התמונה כדי להציג אותה בצורה עגולה
    public Bitmap cropToSquare(Bitmap bitmap)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (width > height) ? height : width;
        int newHeight = (width > height) ? height : width;
        int cropW = (width - newWidth) / 2;
        int cropH = (height - newHeight) / 2;

        return Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);
    }
}