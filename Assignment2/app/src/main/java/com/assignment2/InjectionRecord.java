package com.assignment2;

//Renrui Liu 216166456, SIT207 assignment2.
/*
InjectionRecord class to form the date and injection purpose.
 *  */

public class InjectionRecord {
    private String date;
    private String type;
    private int ID;

    public InjectionRecord(String date, String type) {
        super();
        this.date = date;
        this.type = type;
    }

    public String getDate() {
        return date;
    }
    public int getID(){return ID;}

    public String getType() {
        return type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setID(int ID){this.ID=ID;}
}
