package com.example.amitfinal.UI.ProfileHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.amitfinal.UI.EditProfile.EditProfile;
import com.example.amitfinal.Repository.Moudle.LogShredPre;
import com.example.amitfinal.R;
import com.example.amitfinal.UI.HomePage.HomePage;
import com.example.amitfinal.UI.LogIn1.LogIn1;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProfileHistory extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ProfileHistoryMoudle profileHistoryMoudle;
    private com.example.amitfinal.UI.ProfileHistory.Adapter Adapter;

    // פעולה שמופעלת בזמן פתיחת המסך
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_history);
        profileHistoryMoudle=new ProfileHistoryMoudle(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<LogShredPre> infoList =  profileHistoryMoudle.getShredpre();

        // הצגת הפריטים ברשימה
        Adapter = new Adapter(infoList);
        recyclerView.setAdapter(Adapter);
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
            Intent intent=new Intent(ProfileHistory.this, HomePage.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.profile2)
        {
            Intent intent = new Intent(ProfileHistory.this, EditProfile.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.profile)
        {
            Toast.makeText(this, "you already in profile history", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.logout)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to log out ,it will clear your history");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    profileHistoryMoudle.LogOut();
                    Intent intent1 = new Intent(ProfileHistory.this, LogIn1.class);
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