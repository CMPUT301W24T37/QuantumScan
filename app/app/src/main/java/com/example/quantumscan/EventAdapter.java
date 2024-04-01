package com.example.quantumscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 USER interface Click on the joined Event in the RecyclerView to navigate to the Attendee_EventPage
 to view event information, receive notifications, and scan the QR code to log in, perform the
 following steps:
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final Context context;
    private final List<Event> events;
    private final LayoutInflater inflater;
    private final OnItemClickListener listener;

    // Constructor
    public EventAdapter(Context context, List<Event> events, OnItemClickListener listener) {
        this.context = context;
        this.events = events;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.fragment_attendee, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event currentEvent = events.get(position);
        holder.bind(context, currentEvent, listener);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    // ViewHolder class
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        // UI components for the event item, like TextViews

        public EventViewHolder(View itemView) {
            super(itemView);
            // Initialize your TextViews or ImageViews
        }

        public void bind(final Context context, final Event event, final OnItemClickListener listener) {
            // Bind event data to your views
            itemView.setOnClickListener(v -> listener.onItemClick(event));
        }
    }

    // Interface for click listener
    public interface OnItemClickListener {
        void onItemClick(Event event);
    }
}

