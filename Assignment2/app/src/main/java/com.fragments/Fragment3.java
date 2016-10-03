package com.fragments;

//Renrui Liu 216166456, SIT207 assignment2.

/*
This fragment is Knowledge page which shows a list of websites about hemophilia.
Users can click on them and jump to another activity which has a webview to show the websites.
 *  */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.assignment2.R;

public class Fragment3 extends Fragment {

    ListView lv;
    private View v;
    String[] webs = new String[]{
            "Haemophilia - Wikipedia, the free encyclopedia",
            "Hemophilia - KidsHealth",
            "Guidelines for the management of haemophilia in Australia",
            "Haemophilia - Better Health Channel",
            "Haemophilia | healthdirect",
            "Fitness & Exercise - Living With Hemophilia",
            "Exercises for People with Hemophilia",
            "Physical Therapy in Hemophilia Care Manual - IHTC",
            "How Is Hemophilia Treated?",
            "Hemophilia Treatments and drugs - Mayo Clinic",
            "Factor replacement therapy"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.knowledge, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //set listView
        lv = (ListView) v.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, webs);
        lv.setAdapter(adapter);

        //open website
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Uri uri = Uri.parse("https://en.m.wikipedia.org/wiki/Haemophilia");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    case 1:
                        uri = Uri.parse("http://kidshealth.org/en/teens/hemophilia.html");
                        Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent1);
                        break;
                    case 2:
                        uri = Uri.parse("https://www.blood.gov.au/haemophilia-guidelines");
                        Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent2);
                        break;
                    case 3:
                        uri = Uri.parse("https://www.betterhealth.vic.gov.au/health/conditionsandtreatments/haemophilia");
                        Intent intent3 = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent3);
                        break;
                    case 4:
                        uri = Uri.parse("https://www.healthdirect.gov.au/haemophilia");
                        Intent intent4 = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent4);
                        break;
                    case 5:
                        uri = Uri.parse("http://livingwithhemophilia.ca/managing/fitness-exercise.php");
                        Intent intent5 = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent5);
                        break;
                    case 6:
                        uri = Uri.parse("https://www.haemophilia.ie/uploaded/Exercise_Guide_med.pdf");
                        Intent intent6 = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent6);
                        break;
                    case 7:
                        uri = Uri.parse("http://www.ihtc.org/wp-content/uploads/2013/07/5-PT-Hemophilia-Care-Manual.pdf");
                        Intent intent7 = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent7);
                        break;
                    case 8:
                        uri = Uri.parse("https://www.nhlbi.nih.gov/health/health-topics/topics/hemophilia/treatment");
                        Intent intent8 = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent8);
                        break;
                    case 9:
                        uri = Uri.parse("http://www.mayoclinic.org/diseases-conditions/hemophilia/basics/treatment/con-20029824");
                        Intent intent9 = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent9);
                        break;
                    case 10:
                        uri = Uri.parse("http://www.hemophilia.ca/en/bleeding-disorders/hemophilia-a-and-b/the-treatment-of-hemophilia/factor-replacement-therapy/");
                        Intent intent10 = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent10);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
