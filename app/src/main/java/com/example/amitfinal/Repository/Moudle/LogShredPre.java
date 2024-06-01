package com.example.amitfinal.Repository.Moudle;

public class LogShredPre
{
    String descrip,date;

    // Constructor to initialize the LogShredPre object with description and date
    public LogShredPre(String descrip, String date)
    {
        this.descrip = descrip;
        this.date = date;
    }

    // Getter method to retrieve the description
    public String getDescrip() {
        return descrip;
    }

    // Getter method to retrieve the date
    public String getDate() {
        return date;
    }

    // Setter method to update the description
    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    // Setter method to update the date
    public void setDate(String date) {
        this.date = date;
    }
}
