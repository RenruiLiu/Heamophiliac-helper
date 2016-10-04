package com.fragments;

//Renrui Liu 216166456, SIT207 assignment2.

/*
This fragment is bleeding page which shows a list of bleeding records sorted by date.
Users can make a record with detail description.
 *  */

/*
References:

android date picker dialog
CodeVideo
https://www.youtube.com/watch?v=eVsqDBvgd70

How to delete multiple items in android ListView
Indragni Soft Solutions
https://www.youtube.com/watch?v=luxE7oEKiic

* */


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.assignment2.BleedingDateBaseHelper;
import com.assignment2.BleedingRecord;
import com.assignment2.DateDialog;
import com.assignment2.MyBleedingListAdapter;
import com.assignment2.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment {

    private MyBleedingListAdapter myadapter;
    BleedingDateBaseHelper myDb;
    ListView lv;
    private View v;
    private View temp;
    private Button pickBtn;
    String[] date = new String[100];
    String[] part = new String[100];
    String[] description = new String[100];
    int[] ID=new int[100];
    EditText partTxt;
    EditText descriptionTxt;
    private List<BleedingRecord> bleedingRecordList = new ArrayList<BleedingRecord>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.bleeding, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //init Database
        myDb = new BleedingDateBaseHelper(getContext());
        myDb.getReadableDatabase();

        //get data from database to array.
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(getContext(), R.string.no_record_bleeding, Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < res.getCount() && i < 100; i++) {
                res.moveToPosition(i);
                date[i] = res.getString(1);
                part[i] = res.getString(2);
                description[i]=res.getString(3);
                ID[i]=res.getInt(0);
            }
        }

        //add record Button and TextView
        v.findViewById(R.id.add_bleeding_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRecordDialog();
            }
        });
        v.findViewById(R.id.add_bleeding_recordtxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRecordDialog();
            }
        });

        //set list size
        lv = (ListView) v.findViewById(android.R.id.list);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ViewGroup.LayoutParams laParams = lv.getLayoutParams();
        laParams.height = (int) (dm.heightPixels * 0.73);
        lv.setLayoutParams(laParams);

        //get the number of records in database
        int j = 0;
        for (int i = 0; i < date.length; i++) {
            if (date[i] != null)
                j = j + 1;
        }
        //and then show lines(add record from array to listView).
        if (bleedingRecordList.isEmpty()){
        for (int k = 0; k < j; k++) {
            BleedingRecord bleedingRecord = new BleedingRecord(date[k], part[k], description[k]);
            bleedingRecord.setID(ID[k]);
            bleedingRecordList.add(bleedingRecord);
        }}

        //set arrayAdapter to listView.
        myadapter = new MyBleedingListAdapter(getContext(), R.layout.bleeding_list_row, bleedingRecordList);
        lv.setAdapter(myadapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        //display description
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BleedingRecord br=myadapter.getItem(i);
                String descriptionTxt=br.getDescription();
                AlertDialog.Builder abuilder = new AlertDialog.Builder(getContext());
                abuilder.setTitle(br.getDate()+"      "+br.getPart())
                        .setMessage(descriptionTxt)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .create().show();
            }
        });

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
                                BleedingRecord selecteditem = myadapter.getItem(selected.keyAt(i));
                                myadapter.remove(selecteditem);
                                myDb.getWritableDatabase();
                                myDb.delete(selecteditem.getID());
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


    }

    //add record dialog
    public void AddRecordDialog() {

        LayoutInflater in = LayoutInflater.from(getContext());
        temp = in.inflate(R.layout.add_bleeding_record_dialog, null);

        partTxt=(EditText)temp.findViewById(R.id.part);
        descriptionTxt=(EditText)temp.findViewById(R.id.description);

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
        abuilder.setTitle(R.string.add_new_bleeding_record)
                .setView(temp)
                .setNegativeButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //insert data if selected a date
                        String date1 = pickBtn.getText().toString();
                        if (date1 == getContext().getString(R.string.pick_date)) {
                            Toast.makeText(getContext(), getString(R.string.pls_pick_a_date), Toast.LENGTH_SHORT).show();
                        } else InsertData2DB(date1, partTxt.getText().toString(),descriptionTxt.getText().toString());
                        //InsertData2DB(date1, type);

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

    public void InsertData2DB(String date, String part,String description) {
        myDb.insertData(date, part,description);

        BleedingRecord bleedingRecord = new BleedingRecord(date, part,description);
        bleedingRecordList.add(bleedingRecord);
        myadapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Record is added    " + date + " " + part, Toast.LENGTH_SHORT).show();
    }

}
