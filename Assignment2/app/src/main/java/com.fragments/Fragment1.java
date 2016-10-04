package com.fragments;

//Renrui Liu 216166456, SIT207 assignment2.

/*
This fragment is injection page which shows last and next injection dates, and a list of injection records sorted by date.
Users can add or delete records.
 *  */

/*
References:

android date picker dialog
CodeVideo
https://www.youtube.com/watch?v=eVsqDBvgd70

How to delete multiple items in android ListView
Indragni Soft Solutions
https://www.youtube.com/watch?v=luxE7oEKiic

Android notification at specific date
Wajdi Hh
http://stackoverflow.com/questions/9930683/android-notification-at-specific-date

* */

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.assignment2.DateDialog;
import com.assignment2.InjectionRecord;
import com.assignment2.MyAdapter;
import com.assignment2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.assignment2.DateBaseHelper;
import com.assignment2.Receiver;

public class Fragment1 extends android.support.v4.app.ListFragment {

    DateBaseHelper myDb;
    private View v;
    private ListView lv;
    private MyAdapter myadapter;
    private List<InjectionRecord> injectionRecordList = new ArrayList<InjectionRecord>();
    private View temp;
    private Button pickBtn;
    private String type;
    private RadioGroup rg;
    private TextView lsTime;
    private TextView nxTime;
    private TextView txtNex;
    private TextView txtLas;
    private Button setAlr;
    String lasDate;
    String[] date = new String[100];
    String[] purpose = new String[100];
    int[] ID=new int[100];


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.injection, container, false);
        return v;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lsTime = (TextView) v.findViewById(R.id.lastTime);
        nxTime = (TextView) v.findViewById(R.id.nextTime);
        txtNex = (TextView) v.findViewById(R.id.txtNextTime);
        txtLas = (TextView) v.findViewById(R.id.txtLastTime);
        setAlr=(Button)v.findViewById(R.id.set_alarm);


        //init Database
        myDb = new DateBaseHelper(getContext());
        myDb.getReadableDatabase();

        //get data from database to array.
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(getContext(), R.string.no_record_injection, Toast.LENGTH_SHORT).show();
            //hide the texts
            txtLas.setText("");
            txtNex.setText("");
            setAlr.setBackground(null);
        } else {
            for (int i = 0; i < res.getCount() && i < 100; i++) {
                res.moveToPosition(i);
                date[i] = res.getString(1);
                purpose[i] = res.getString(2);
                ID[i]=res.getInt(0);

                //set last and next injection date.
                res.moveToFirst();
                lasDate = res.getString(1);
                lsTime.setText(lasDate);
                try {
                    nxTime.setText(Date2String(Cal2Date(GetNextDate(lasDate, 3))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        //get the number of records in database
        int j = 0;
        for (int i = 0; i < date.length; i++) {
            if (date[i] != null)
                j = j + 1;
        }
        if (injectionRecordList.isEmpty()){
        //and then show lines(add record from array to listView).
        for (int k = 0; k < j; k++) {
            InjectionRecord injectionRecord = new InjectionRecord(date[k], purpose[k]);
            injectionRecord.setID(ID[k]);
            injectionRecordList.add(injectionRecord);
        }}


        //set list size
        lv = (ListView) v.findViewById(android.R.id.list);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ViewGroup.LayoutParams laParams = lv.getLayoutParams();
        laParams.height = (int) (dm.heightPixels * 0.58);
        lv.setLayoutParams(laParams);

        //set arrayAdapter to listView.
        myadapter = new MyAdapter(getContext(), R.layout.injection_list_row, injectionRecordList);
        lv.setAdapter(myadapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        //handle the multi choice mode to delete records.
        lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                final int checkedCount = lv.getCheckedItemCount();
                actionMode.setTitle(checkedCount + getString(R.string.records_selected));
                myadapter.toggleSelection(i);
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.getMenuInflater().inflate(R.menu.menu_fragment1, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.delete:
                        SparseBooleanArray selected = myadapter.getSelectedIds();
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                InjectionRecord selecteditem = myadapter.getItem(selected.keyAt(i));
                                myadapter.remove(selecteditem);
                                myDb.getWritableDatabase();
                                myDb.delete(selecteditem.getID());
                                RefreshActivity();
                            }
                        }
                        actionMode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                myadapter.removeSelection();
            }
        });

        //add record Button and TextView
        v.findViewById(R.id.add_recordbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRecordDialog();
            }
        });
        v.findViewById(R.id.add_recordtxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRecordDialog();
            }
        });

        //set notification
        setAlr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //get next Injection date
                    Calendar cal=GetNextDate(lasDate,3);
                    Date strDate=Cal2Date(cal);
                    if (System.currentTimeMillis()>strDate.getTime()){
                        Toast.makeText(getContext(),"The recommend date has passed",Toast.LENGTH_SHORT).show();}
                    else {
                        cal.set(Calendar.HOUR_OF_DAY, 11);
                        cal.set(Calendar.MINUTE, 59);

                        //set alarm
                        AlarmManager alarms = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Receiver receiver = new Receiver();
                        IntentFilter filter = new IntentFilter("ALARM_ACTION");
                        getContext().registerReceiver(receiver, filter);
                        Intent intent = new Intent("ALARM_ACTION");
                        intent.putExtra("Toast", "It's time to inject");
                        PendingIntent operation = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                        alarms.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), operation);

                        Toast.makeText(getContext(), "Set alarm at " + cal.getTime().toString(), Toast.LENGTH_LONG).show();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //add record dialog
    public void AddRecordDialog() {

        final LayoutInflater in = LayoutInflater.from(getContext());
        temp = in.inflate(R.layout.add_record_dialog, null);

        //radioButtons
        type="Prevent";
        rg = (RadioGroup) temp.findViewById(R.id.r_g);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //get type from radio button
                if (i == R.id.prevent)
                    type = "Prevent";
                else type = "Demand";
            }
        });

        //DatePickerDialog inside this dialog
        pickBtn = (Button) temp.findViewById(R.id.pick);
        pickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog dialog = new DateDialog(view);
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "DatePicker");
            }
        });

        //Add Record Dialog
        AlertDialog.Builder abuilder = new AlertDialog.Builder(getContext());
        abuilder.setTitle(R.string.add_record)
                .setView(temp)
                .setNegativeButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //insert data if selected a date
                        String date1 = pickBtn.getText().toString();
                        if (date1 == getContext().getString(R.string.pick_date)) {
                            Toast.makeText(getContext(), getString(R.string.pls_pick_a_date), Toast.LENGTH_SHORT).show();
                        }
                        else InsertData2DB(date1, type);
                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .create().show();
    }


    public static Calendar GetNextDate(String strTime, int i)
            throws ParseException {

        //convert string date to Date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        date = formatter.parse(strTime);

        //add i days
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, i);
        return cal;
    }

    public Date Cal2Date(Calendar cal)
    {
        Date date = null;
        date=cal.getTime();
        return date;
    }

    public String Date2String(Date date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fixedDate = formatter.format(date);
        return fixedDate;
    }


    //insert data to db
    public void InsertData2DB(String date, String type) {
        myDb.insertData(date, type);

        InjectionRecord injectionRecord = new InjectionRecord(date, type);
        injectionRecordList.add(injectionRecord);
        myadapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Record is added    " + date + " " + type, Toast.LENGTH_SHORT).show();
        RefreshActivity();
    }

    public void RefreshActivity()
    {
        getActivity().finish();
        startActivity(getActivity().getIntent());
    }

}


