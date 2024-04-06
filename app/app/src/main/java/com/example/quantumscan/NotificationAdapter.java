package com.example.quantumscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NotificationAdapter extends ArrayAdapter<String> {
    private ArrayList<String> Announcement;
    public NotificationAdapter(Context context, ArrayList<String> Announcement) {
        super(context, 0, Announcement);
        this.Announcement = Announcement;
    }
    public int getSize(){
        return this.Announcement.size();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(super.getContext()).inflate(R.layout.history_announcement, parent, false);
        } else {
            view = convertView;
        }
        String announcement = super.getItem(position);

        TextView announcementText = view.findViewById(R.id.h_announcement);
;

        announcementText.setText(announcement);

        return view;
    }

}
