package com.xpenditure.www.xpenditure;

/**
 * Created by Swaraj on 26-04-2018.
 */

public class DisplayData {
    private String Day;
    private String note;
    private String entered;

    public DisplayData(){

    }

    public DisplayData(String day, String note, String entered) {
        Day = day;
        this.note = note;
        this.entered = entered;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEntered() {
        return entered;
    }

    public void setEntered(String entered) {
        this.entered = entered;
    }
}
