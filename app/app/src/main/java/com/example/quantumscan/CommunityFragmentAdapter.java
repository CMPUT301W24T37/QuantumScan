package com.example.quantumscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quantumscan.databinding.AnnoucementBinding;

import java.util.ArrayList;

public class CommunityFragmentAdapter extends ArrayAdapter<Announcement> {

    private ArrayList<Announcement> Announcement;
    public CommunityFragmentAdapter(Context context, ArrayList<Announcement> Announcement) {
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
            view = LayoutInflater.from(super.getContext()).inflate(R.layout.annoucement, parent, false);
        } else {
            view = convertView;
        }
        Announcement announcement = super.getItem(position);





        TextView announcementText = view.findViewById(R.id.annoucement);
        TextView organizerText= view.findViewById(R.id.organizer);
        TextView time = view.findViewById(R.id.time);

        announcementText.setText(announcement.getEvent() + ": " + announcement.getAnnouncment());
        organizerText.setText(announcement.getOrganizer());
        time.setText(announcement.getTime());

        return view;
    }















}
