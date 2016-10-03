package com.assignment2;
//Renrui Liu 216166456, SIT207 assignment2.

/*
This is the DateDialog to pick a date.
 *  */

/* References
android date picker dialog
CodeVideo
https://www.youtube.com/watch?v=eVsqDBvgd70
* */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DateDialog extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {

    TextView txtDate;
    String date;

    public DateDialog(View view) {
        txtDate = (TextView) view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public String getDate() {
        return date;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        date = year + "-" + DateFix(month + 1) + "-" + DateFix(day);
        txtDate.setText(date);
    }

    private static String DateFix(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

}
