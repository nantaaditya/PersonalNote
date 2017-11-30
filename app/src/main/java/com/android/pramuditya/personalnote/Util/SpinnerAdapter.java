package com.android.pramuditya.personalnote.Util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.pramuditya.personalnote.model.Tag;

import java.util.ArrayList;

/**
 * Created by User on 25/11/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<Tag> {
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<Tag> tags;

    public SpinnerAdapter(Context context, int textViewResourceId,
                          ArrayList<Tag> tags) {
        super(context, textViewResourceId, tags);
        this.context = context;
        this.tags = tags;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Tag getItem(int position) {
        return tags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setPadding(5,5,5,5);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(tags.get(position).getName());
        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setPadding(5,5,5,5);
        label.setText(tags.get(position).getName());
        return label;
    }
}
