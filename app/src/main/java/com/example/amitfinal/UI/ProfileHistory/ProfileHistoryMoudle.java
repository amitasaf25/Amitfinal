package com.example.amitfinal.UI.ProfileHistory;

import android.content.Context;

import com.example.amitfinal.Repository.Moudle.LogShredPre;
import com.example.amitfinal.Repository.Repository;

import java.util.List;

public class ProfileHistoryMoudle {
    private Repository repository;
    private Context c;

    // Constructor to initialize the module with the context
    public ProfileHistoryMoudle(Context c)
    {
        this.c = c;
        repository = new Repository(c);
    }

    // Method to retrieve the list of log items from the repository
    public List<LogShredPre> getShredpre()
    {
        return repository.getShredpre();
    }

    // Method to perform logout action
    public void LogOut()
    {
        repository.LogOut();
    }
}

