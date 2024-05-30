package com.example.amitfinal.Repository.Moudle;

public class LogShredPre
{
    String descrip,date;

    public LogShredPre(String descrip, String date)
    {
        this.descrip = descrip;
        this.date = date;
    }

    public String getDescrip() {
        return descrip;
    }

    public String getDate() {
        return date;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
