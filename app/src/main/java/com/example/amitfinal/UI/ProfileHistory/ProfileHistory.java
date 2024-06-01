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

public class ProfileHistory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProfileHistoryMoudle profileHistoryMoudle;
    private com.example.amitfinal.UI.ProfileHistory.Adapter Adapter;

    // Method invoked when the screen is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_history);
        profileHistoryMoudle = new ProfileHistoryMoudle(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve list of log items from the module
        List<LogShredPre> infoList = profileHistoryMoudle.getShredpre();

        // Display the items in a RecyclerView
        Adapter = new Adapter(infoList);
        recyclerView.setAdapter(Adapter);
    }

    // Method to create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Method to handle menu item selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Handle menu item clicks
        if (id == R.id.home) {
            // Navigate to home page
            Intent intent = new Intent(ProfileHistory.this, HomePage.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.profile2) {
            // Navigate to edit profile page
            Intent intent = new Intent(ProfileHistory.this, EditProfile.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.profile) {
            // Display a toast indicating the user is already in profile history
            Toast.makeText(this, "You are already in profile history", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.logout) {
            // Confirm logout and clear history
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to log out? This will clear your history.");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Perform logout action
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
