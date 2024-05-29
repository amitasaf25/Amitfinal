package com.example.amitfinal.UI.ProfileHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.amitfinal.EditProfile;
import com.example.amitfinal.LogShredPre;
import com.example.amitfinal.R;
import com.example.amitfinal.Repository.Repository;
import com.example.amitfinal.UI.HomePage.HomePage;
import com.example.amitfinal.UI.LogIn1.LogIn1;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProfileHistory extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private com.example.amitfinal.UI.ProfileHistory.Adapter Adapter;

    private Repository repository;
    // פעולה שמופעלת בזמן פתיחת המסך
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_history);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository=new Repository(this);
        List<LogShredPre> infoList =  repository.getShredpre();

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
        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent1=new Intent(ProfileHistory.this, LogIn1.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent1);
            return true;
        } else
        {
            return super.onOptionsItemSelected(item);
        }
    }
}