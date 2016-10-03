package com.assignment2;

//Renrui Liu 216166456, SIT207 assignment2.

/*
BleedingRecord class to form the date and which part of body and description.
 *  */

public class BleedingRecord {
    private String date;
    private String part;
    private String description;
    private int ID;

    public BleedingRecord(String date, String part,String description) {
        super();
        this.date = date;
        this.part = part;
        this.description=description;
    }

    public String getDate() {
        return date;
    }

    public String getPart() {
        return part;
    }

    public String getDescription() {
        return description;
    }

    public int getID(){return ID;}

    public void setDate(String date) {
        this.date = date;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setID(int ID){this.ID=ID;}
}
