package com.example.quantumscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AttendeeContentAdapter extends ArrayAdapter<Attendee> {
    public AttendeeContentAdapter(Context context, ArrayList<Attendee> users) {
        super(context, 0, users);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Attendee attendee = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.attendee_list_content, parent, false);
        }
        // Lookup view for data population
        TextView nameTextView = (TextView) convertView.findViewById(R.id.attendee_name);
        TextView checkCountTextView = (TextView) convertView.findViewById(R.id.check_in_num);
        ImageView checkStatusView = (ImageView) convertView.findViewById(R.id.check_in_status);
        // Populate the data into the template view using the data object
        nameTextView.setText(attendee.getName());
        checkCountTextView.setText(String.valueOf(attendee.getCheckInCount()));
        if(attendee.isCheckedIn()){
            checkStatusView.setImageResource(R.drawable.ic_checked);
        }else {
            checkStatusView.setImageResource(R.drawable.ic_unchecked);;
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
