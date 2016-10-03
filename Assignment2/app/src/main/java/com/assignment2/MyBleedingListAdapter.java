package com.assignment2;
//Renrui Liu 216166456, SIT207 assignment2.

/*
ArrayAdapter for bleeding records listView.
 *  */

/*
References:

How to delete multiple items in android ListView
Indragni Soft Solutions
https://www.youtube.com/watch?v=luxE7oEKiic

* */

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyBleedingListAdapter extends ArrayAdapter<BleedingRecord> {

    private Context context;
    List<BleedingRecord> bleedingRecordList;
    LayoutInflater inflater;
    private SparseBooleanArray mSelectedItemsIds;

    public MyBleedingListAdapter(Context context, int resourceId, List<BleedingRecord> bleedingRecordList) {
        super(context, resourceId, bleedingRecordList);

        this.context = context;
        mSelectedItemsIds = new SparseBooleanArray();
        this.bleedingRecordList = bleedingRecordList;
        inflater = LayoutInflater.from(context);
    }

    //ViewHolder
    private class ViewHolder {
        TextView date;
        TextView part;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.bleeding_list_row, null);
            holder.date = (TextView) view.findViewById(R.id.bleeding_date);
            holder.part = (TextView) view.findViewById(R.id.bleeding_part);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.date.setText(bleedingRecordList.get(position).getDate());
        holder.part.setText(bleedingRecordList.get(position).getPart());
        return view;
    }

    public void remove(InjectionRecord object) {
        bleedingRecordList.remove(object);
        notifyDataSetChanged();
    }

    public List<BleedingRecord> getInjectionRecord() {
        return bleedingRecordList;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value) {
            mSelectedItemsIds.put(position, value);
        } else {
            mSelectedItemsIds.delete(position);
        }
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}