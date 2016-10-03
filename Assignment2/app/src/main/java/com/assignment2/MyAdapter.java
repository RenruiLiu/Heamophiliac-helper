package com.assignment2;
//Renrui Liu 216166456, SIT207 assignment2.

/*
ArrayAdapter for injection records listView.
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

public class MyAdapter extends ArrayAdapter<InjectionRecord> {

    private Context context;
    List<InjectionRecord> injectionRecordList;
    LayoutInflater inflater;
    private SparseBooleanArray mSelectedItemsIds;

    public MyAdapter(Context context, int resourceId, List<InjectionRecord> injectionRecordList) {
        super(context, resourceId, injectionRecordList);

        this.context = context;
        mSelectedItemsIds = new SparseBooleanArray();
        this.injectionRecordList = injectionRecordList;
        inflater = LayoutInflater.from(context);
    }

    //ViewHolder
    private class ViewHolder {
        TextView date;
        TextView type;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.injection_list_row, null);
            holder.date = (TextView) view.findViewById(R.id.injection_date);
            holder.type = (TextView) view.findViewById(R.id.injection_purpose);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.date.setText(injectionRecordList.get(position).getDate());
        holder.type.setText(injectionRecordList.get(position).getType());
        return view;
    }

    public void remove(InjectionRecord object) {
        injectionRecordList.remove(object);
        notifyDataSetChanged();
    }

    public List<InjectionRecord> getInjectionRecord() {
        return injectionRecordList;
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