package com.example.quantumscan;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FireStoreBridge implements OrganizerCreateEvent.imageUrlUploadListener{
    private FirebaseFirestore db;
    private CollectionReference collectionName;
    private Query query;

    public FireStoreBridge(String collectionName){
        this.db = FirebaseFirestore.getInstance();
        this.collectionName = this.db.collection(collectionName);
        this.query = this.collectionName;

    }
    private FirebaseFirestore getDb() {
        return db;
    }

    private CollectionReference getCollectionName() {
        return collectionName;
    }

    public interface OnUserRetrievedListener {
        void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles);
    }

    public interface OnEventRetrievedListener {
        void onEventRetrieved(ArrayList<Event> event, ArrayList<String> organizerList);

    }

    private interface OnEventRetrievedListenerHelper{
        void onAttendeeRetrieved(ArrayList<AttendeeFireBaseHolder> attendeeList);
    }


    public void retrieveUser(String userID, OnUserRetrievedListener listener) {
        this.query = this.collectionName.whereEqualTo(FieldPath.documentId(), userID);

        this.query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    User user = new User(null,null,null, null,null); // Create a new User object
                    ArrayList<String> attendeeRoles = new ArrayList<String>();
                    ArrayList<String> organizerRoles = new ArrayList<String>();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        // Retrieve user data from document and set properties of the User object
                        user.setName(documentSnapshot.getString("name"));
                        user.setProfilePicture(documentSnapshot.getString("profilePicture"));
                        user.setPhone(documentSnapshot.getString("phone"));
                        user.setUniversity(documentSnapshot.getString("university"));
                        user.setEmail(documentSnapshot.getString("email"));
                        List<String> list1 = (List<String>) documentSnapshot.get("attendeeRoles");
                        List<String> list2 = (List<String>) documentSnapshot.get("organizerRoles");
                        attendeeRoles = (ArrayList<String>) list1;
                        organizerRoles = (ArrayList<String>) list2;

                    }

                    // Notify the listener with the retrieved user object is complete
                    listener.onUserRetrieved(user, attendeeRoles, organizerRoles);
                } else {
                    // Handle the case where the task failed
                    Exception e = task.getException();
                    System.out.println("Query failed: " + e.getMessage());
                    // Notify the listener with a null user object
                    listener.onUserRetrieved(null,null,null);
                }
            }
        });
    }
    public void retrieveEvent(String eventId, OnEventRetrievedListener listener){
        this.query = this.collectionName.whereEqualTo(FieldPath.documentId(), eventId);
        this.query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Event> eventList = new ArrayList<>();
                    ArrayList<String> organizerIdList = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot0 : task.getResult()) {
                        // read each event
                        Event event = new Event();
                        //OrganizerFireBaseHolder organizer = new OrganizerFireBaseHolder();
                        //retrieve event information
                        event.setId(documentSnapshot0.getString("id"));
                        event.setAnnouncement((ArrayList<String>) documentSnapshot0.get("announcements"));
                        event.setPosterCode(documentSnapshot0.getString("posterCode"));
                        event.setEventCode(documentSnapshot0.getString("eventCode"));
                        event.setTitle(documentSnapshot0.getString("title"));
                        event.setDescription(documentSnapshot0.getString("description"));
                        organizerIdList.add(documentSnapshot0.getString("organizer"));
                        // TODO: for check in data retrieve
                        //retrieve Organizer info
                        //CollectionReference attendeeListRef = getCollectionName();
                        //retrieve attendee belong to this event
                        // retrieveAllEventHelper(attendeeListRef, organizer);
                        //organizerList.add(organizer);
                        eventList.add(event);
                    }
                    // Notify the listener with the retrieved user object is complete
                    listener.onEventRetrieved(eventList, organizerIdList);
                } else {
                    // Handle the case where the task failed
                    Exception e = task.getException();
                    System.out.println("Query failed: " + e.getMessage());
                    // Notify the listener with a null user object

                }
            }
        });
    }
    public void retrieveAllEvent(OnEventRetrievedListener listener) {
        this.query = this.collectionName;

        this.query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Event> eventList = new ArrayList<>();
                    ArrayList<String> organizerIdList = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot0 : task.getResult()) {
                        // read each event
                        Event event = new Event();
                        //OrganizerFireBaseHolder organizer = new OrganizerFireBaseHolder();
                        //retrieve event information
                        event.setId(documentSnapshot0.getString("id"));
                        event.setAnnouncement((ArrayList<String>) documentSnapshot0.get("announcements"));
                        event.setPosterCode(documentSnapshot0.getString("posterCode"));
                        event.setEventCode(documentSnapshot0.getString("eventCode"));
                        event.setTitle(documentSnapshot0.getString("title"));
                        event.setDescription(documentSnapshot0.getString("description"));
                       organizerIdList.add(documentSnapshot0.getString("organizer"));
                        // TODO: for check in data retrieve
                        //retrieve Organizer info
                        //CollectionReference attendeeListRef = getCollectionName();
                        //retrieve attendee belong to this event
                        // retrieveAllEventHelper(attendeeListRef, organizer);
                        //organizerList.add(organizer);
                        eventList.add(event);
                    }
                    // Notify the listener with the retrieved user object is complete
                    listener.onEventRetrieved(eventList, organizerIdList);
                } else {
                    // Handle the case where the task failed
                    Exception e = task.getException();
                    System.out.println("Query failed: " + e.getMessage());
                    // Notify the listener with a null user object

                }
            }
        });
    }
    private void retrieveAllEventHelper(CollectionReference attendeeListRef, OrganizerFireBaseHolder organizer){
        // TODO: This is for check in status retrieve
        /*
        retrieve attendeelist in each event documentation
         */
        attendeeListRef.document().collection("attendeeList").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<AttendeeFireBaseHolder> attendeeFireBaseHolders = new ArrayList<>();

                            for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()) {
                                AttendeeFireBaseHolder attendee = new AttendeeFireBaseHolder();
                                attendee.setAttendeeId(documentSnapshot1.getString("id"));
                                attendee.setCheckInStatus(documentSnapshot1.getBoolean("checkedIn"));
                                attendeeFireBaseHolders.add(attendee);
                            }

                        }else {
                            // Handle the case where the task failed
                            Exception e = task.getException();
                            System.out.println("Query failed: " + e.getMessage());
                            // Notify the listener with a null user object

                        }

                    }
                });
    }

    public void updateUser(User user){


        String userID = user.getId();
        this.collectionName.document(userID)
                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Welcome !");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Please try when you are connected to the internet", e);
                    }
                });

    }


    public void updateEvent(Event eventInfo, String organizerID){
        // get event id
        String eventId= eventInfo.getId();
        System.out.println(eventId);

        // create an attendeeList that will be added to organizer <both are firebase holder>
        ArrayList<AttendeeListFireBaseHolder> attendeeList = new ArrayList<>();
        for (int i = 0; i < eventInfo.getAttendees().size(); i++){
            AttendeeListFireBaseHolder attendee = new AttendeeListFireBaseHolder(
                    eventInfo.getAttendees().get(i).getUserID(),
                    eventInfo.getAttendees().get(i).getCheckIn(),
                    eventInfo.getAttendees().get(i).getUserName(),
                    eventInfo.getAttendees().get(i).getCheckInAccount());
            attendeeList.add(attendee);
        }//eventInfo.getOrganizer().getUser().getId() eventInfo.getOrganizer().getUser().getId()
        EventFireBaseHolder event = new EventFireBaseHolder(
                eventInfo.getAnnouncement(),
                eventInfo.getDescription(),
                eventInfo.getEventCode(),
                eventId,
                organizerID,
                eventInfo.getPosterCode(),
                eventInfo.getTitle());
        System.out.println("uploading");
        this.collectionName.document(eventId).set(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("event upload successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("event upload failed");
                    }
                });

        // check if this
        System.out.println("uploading");

    }

    public void updateEventDescription(String eventId, String description){

        this.collectionName.document(eventId).update("description", description)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Welcome !");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Please try when you are connected to the internet", e);
                    }
                });
    }
    @Override
    public void updateEventImage(String eventId, String imageURL){
        this.collectionName.document(eventId).update("posterCode", imageURL).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Welcome !");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Please try when you are connected to the internet", e);
                    }
                });

    }
}
