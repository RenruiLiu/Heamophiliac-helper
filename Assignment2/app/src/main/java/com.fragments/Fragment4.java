package com.fragments;

//Renrui Liu 216166456, SIT207 assignment2.

/*
This fragment is Other page which shows a list of websites about hemophilia.
Users can click on them and jump to another activity which has a webview to show the websites.
 *  */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.assignment2.AboutActivity;
import com.assignment2.PlayVideoActivity;
import com.assignment2.R;


public class Fragment4 extends Fragment{

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "AOP_PREFS";
    ListView lv;
    View v;
    private View temp;
    String[] options = new String[]{"Guide","Your Doctor","About Me"};
    String number;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.other,container,false);
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //set listView
        lv = (ListView) v.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, options);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        //play video
                        Intent intent0 =new Intent(getContext(), PlayVideoActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        //set doctor number
                        DisplayDoctor();
                        break;
                    case 2:
                        //display about page
                        Intent intent2 =new Intent(getContext(), AboutActivity.class);
                        startActivity(intent2);
                        break;

                    default:break;
                }

            }
        });
    }

    //the dialog shows the information about doctor and number.
    public void DisplayDoctor()
    {
        LayoutInflater in = LayoutInflater.from(getContext());
        temp = in.inflate(R.layout.your_doctor, null);

        TextView doc_name_txt = (TextView)temp.findViewById(R.id.doc_name_txt);
        TextView doc_number_txt = (TextView)temp.findViewById(R.id.doc_number_txt);

        //get name and number from sharedPreferences
        doc_name_txt.setText(sharedPreferences.getString("name",null));
        doc_number_txt.setText(sharedPreferences.getString("number",null));

        AlertDialog.Builder abuilder = new AlertDialog.Builder(getContext());
        abuilder.setTitle("Your Doctor")
                .setView(temp)
                .setNegativeButton("Update",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UpdateDoc();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .create().show();
    }

    public void UpdateDoc()
    {
        LayoutInflater in=LayoutInflater.from(getContext());
        View t = in.inflate(R.layout.new_doctor,null);
        final EditText doc_name=(EditText)t.findViewById(R.id.doc_name);
        final EditText doc_number=(EditText)t.findViewById(R.id.doc_number);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Set new doctor")
                .setView(t)
                .setNegativeButton("Save",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        number=doc_number.getText().toString();
                        //save name and number to sharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", doc_name.getText().toString());
                        editor.putString("number",number);
                        editor.apply();
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

}
