package com.example.amitfinal.UI.ProfileHistory;

import android.content.Context;

import com.example.amitfinal.Repository.Moudle.LogShredPre;
import com.example.amitfinal.Repository.Repository;

import java.util.List;

public class ProfileHistoryMoudle
{
    private Repository repository;
    private Context c;

    public ProfileHistoryMoudle(Context c)
    {
        this.c = c;
        repository=new Repository(c);
    }

    public List<LogShredPre> getShredpre()
    {
     return repository.getShredpre();
    }

    public void LogOut()
    {
        repository.LogOut();
    }
}
