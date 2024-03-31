package com.example.quantumscan;

import static android.content.ContentValues.TAG;

import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


//Todo: Settle down the structure of the back-end Models (Attendee Organizer User Event)
public class Admin {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private CollectionReference collectionName;
    private Query query;
    private FirebaseStorage storage;
    //private FireStoreBridge fbUser = new FireStoreBridge("USER");
    //private FireStoreBridge fbEvent = new FireStoreBridge("EVENT");
    //boolean result;

    public Admin() {
    }

//    public interface OnDocumentsDeletedListener {
//        void onDocumentsDeleted(String eventID);
//    }


    public interface OnEventRetrievedListener {
        void onEventRetrieved(ArrayList<Event> events);

    }

    public interface OnEventAttendeeListRetrievedListener {
        void onEventAttendeeListRetrieved(ArrayList<Attendee> attendees);

    }

    public interface OnUserRetrievedListener {
        void onUserRetrieved(ArrayList<User> users);

    }


    // delete the sub-collection <attendeeList> in the event. (if you want to call this method, change 'private' to 'public')
    // note: the reason why this exist is because to fully delete a collection, you need to manually delete its sub-collections by removing all their documents
    // source1: https://firebase.google.com/docs/firestore/manage-data/delete-data#delete_documents
    // source2: https://firebase.google.com/docs/firestore/manage-data/delete-data#collections
    private void deleteAttendeeListSubCollection(String eventID) {
        //result = true;
        db.collection("EVENT").document(eventID)
                .collection("attendeeList")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "start deleting attendeeList sub-collectoin by deleting its all documents in " + eventID + " event");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, "delete..." + document.getData());
                            document.getReference().delete();
                            Log.d(TAG, "delete...finished");
                            // Handle each document deletion (e.g., log or UI update)
                        }
                    } else {
                        // Handle the error
                        Log.d(TAG, "(delete subcoll. attendeeList failed in "+eventID+") Failed with: ", task.getException());
                    }
                });
    }

    // Pass the userid and eventid, this method will delete the eventid in the user's AttendeeRoles
    public void deleteEventFromAttendeeUser(String userID, String eventID) {
        //result = true;
        db.collection("USER").document(userID)
                .update("attendeeRoles", FieldValue.arrayRemove(eventID))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Log.d(TAG, "Deleting event from AttendeeRoles:\nstart deleting "+eventID+" in " + userID + " user");
                        //db.collection("USER").document(userID).update("attendeeRoles", FieldValue.arrayRemove(eventID));
                        Log.d(TAG, "deleting ended");
                    } else {
                        // Handle the error
                        Log.w(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    // Pass the userid and eventid, this method will delete the eventid in the user's OrganizerRoles
    public void deleteEventFromOrganizerUser(String userID, String eventID) {
        //result = true;
        db.collection("USER").document(userID)
                .update("organizerRoles", FieldValue.arrayRemove(eventID))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Log.d(TAG, "Deleting event from OrganizerRoles:\nstart deleting "+eventID+" in " + userID + " user");
                        //db.collection("USER").document(userID).update("organizerRoles", FieldValue.arrayRemove(eventID));
                        Log.d(TAG, "deleting ended?");
                    } else {
                        // Handle the error
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    // remove user profile
    public void deleteUserProfile(String userID) {
        DocumentReference userRef = db.collection("USER").document(userID);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Log.d(TAG, "user " + userID + " found");
                    //userRef.update("name", "Default Name");
                    userRef.update("university", "");
                    userRef.update("phone", "");
                    userRef.update("email", "");
                    Log.d(TAG, "deleting finished");
                }
                else {
                    Log.w(TAG, "document "+ userID +" does not exist");
                }

            }
        })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "failed to load document (user "+userID+")");
                }
        });
    }

    // remove user avatar
    public void deleteUserAvatar(String userID) {
        DocumentReference userRef = db.collection("USER").document(userID);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Log.d(TAG, "user " + userID + " found");
                            userRef.update("profilePicture", "DEFAULT_PFP");  // Todo: call default pfp method there
                            Log.d(TAG, "deleting user's avatar finished");
                        }
                        else {
                            Log.w(TAG, "document "+ userID +" does not exist");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "failed to load document (user "+userID+")");
                    }
                });
    }

    public void deleteEventPoster(String eventID) {
        DocumentReference eventRef = db.collection("EVENT").document(eventID);
        eventRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Log.d(TAG, "event " + eventID + " found");
                            eventRef.update("posterCode", "");
                            Log.d(TAG, "deleting event's poster finished");
                        }
                        else {
                            Log.w(TAG, "document "+ eventID +" does not exist");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "failed to load document (event "+eventID+")");
                    }
                });
    }


    private void deleteEventHelper(String eventID) {
        // 4.01 delete the sub-collection of the event
        SystemClock.sleep(3000);
        this.deleteAttendeeListSubCollection(eventID);
        // 4 delete event
        //SystemClock.sleep(1500);
        db.collection("EVENT").document(eventID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "(event) DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "(delete rest of fields of event failed) Error deleting document", e);
                    }
                });
    }

    // Todo: known issue: maybe b/c it is async, the the caller will receive neither true or false (idk how to do callback and stuff)
    // Todo: meaning the return value won't work which may lead to unexpected results (so I change the return type to 'void')
    // it takes at least 2 seconds to delete.
    public void removeEvent(String eventID) {
        // precondition: attendee in the event has the event id in their attendeeRoles. organizer of the event has the event id in their organizerRoles.
        // 1. check if event exists
        // 1E. it actually doesn't matter idk why I did step one
        // 2. while event still exists, loop through Attendee user id list
        // 2.1. for every user id, locate the user in Firebase, remove the event id from their attendeeRoles list
        // 3. while event still exists, get the Organizer user id
        // 3.1. locate the user in Firebase, remove the event id from their organizerRoles list
        // 4. delete the event document from Firebase


//        // 1. check if event exists
//        DocumentReference docIdRef = db.collection("EVENT").document(eventID);
//        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "Document exists! \npassed event id " + eventID + "\nretrieve event id is " + document.getId());
//
////                        fbEvent.retrieveEvent(eventID,new FireStoreBridge.OnEventRetrievedListener() {
////                            @Override
////                            public void onEventRetrieved(ArrayList<Event> event1, ArrayList<String> organizerList) {
////                                event = event1.get(0);
////                            }
////                        });
//
//                    } else {
//                        Log.d(TAG, "Document does not exist with event id "+eventID+"! (can not find event failed)");
//                    }
//                } else {
//                    Log.d(TAG, "(delete event failed) Failed with: ", task.getException());
//                }
//            }
//        });

        //SystemClock.sleep(1500);
        // 2. Attendee...
        db.collection("EVENT").document(eventID)
                .collection("attendeeList")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "start iterating attendeeList subcollectoin in " + eventID + " event");
                        // 2.1. for every user id, locate the user in Firebase, remove the event id from their attendeeRoles list
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, "loop to " + document.getId() + document.getData());
                            this.deleteEventFromAttendeeUser(document.getId(), eventID);
                            Log.d(TAG, "finished iterating the user " + document.getId());
                        }
                    } else {
                        // Handle the error
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                });

        SystemClock.sleep(1500);
        // 3. while event still exists, get the Organizer user id
        db.collection("EVENT").document(eventID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // 3.1. locate the user in Firebase, remove the event id from their organizerRoles list
                        Log.d(TAG, "Locating the organizer ID from " + eventID + " event");
                        DocumentSnapshot document = task.getResult();
                        //String organizerUserId = document.getString("organizer");
                        Log.d(TAG, "The organizer's user id in " + eventID + " is " + document.getString("organizer") + "\nstart deleting event from user's organizerRoles");
                        this.deleteEventFromOrganizerUser(document.getString("organizer"), eventID);
                        Log.d(TAG, "finished deleting event from user's organizerRoles");
                    } else {
                        // Handle the error
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                });

        // 4.01 delete the sub-collection of the event
        // 4 delete event
        this.deleteEventHelper(eventID);
    }

    // get one single event, store in the first position of the arraylist (eventList)
    // Note: this method won't contain attendeeList, this will be empty arraylist or null.
    public void retrieveEvent(String eventId, Admin.OnEventRetrievedListener listener){
        this.query = this.db.collection("EVENT").whereEqualTo(FieldPath.documentId(), eventId);
        this.query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Event> eventList = new ArrayList<>();
                    //ArrayList<String> organizerIdList = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot0 : task.getResult()) {
                        // read each event
                        Log.d(TAG, "Current document: " + documentSnapshot0.getId());
                        Event event = new Event();
                        //OrganizerFireBaseHolder organizer = new OrganizerFireBaseHolder();
                        //retrieve event information
                        event.setId(documentSnapshot0.getString("id"));
                        event.setAnnouncement((ArrayList<String>) documentSnapshot0.get("announcements"));
                        event.setPosterCode(documentSnapshot0.getString("posterCode"));
                        event.setEventCode(documentSnapshot0.getString("eventCode"));
                        event.setTitle(documentSnapshot0.getString("title"));
                        event.setDescription(documentSnapshot0.getString("description"));
                        event.setOrganizer(documentSnapshot0.getString("organizer"));
                        eventList.add(event);
                    }
                    // Notify the listener with the retrieved user object is complete
                    listener.onEventRetrieved(eventList);
                } else {
                    // Handle the case where the task failed
                    Exception e = task.getException();
                    System.out.println("Query failed: " + e.getMessage());
                    // Notify the listener with a null user object
                    listener.onEventRetrieved(null);
                }
            }
        });
    }
    // Usage:
    /* //ex: print one event's info (except attendeeList)
    admin.retrieveAllEvent(new Admin.OnEventRetrievedListener() {
            @Override
            public void onEventRetrieved(ArrayList<Event> events) {
                int counter = 0;
                for (Event event : events) {  // there is always only one event in events, you can also access it using events.get(0)
                    counter++;
                    Log.d(TAG, "Event " + String.valueOf(counter));
                    Log.d(TAG, event.getId());
                    Log.d(TAG, event.getEventCode());
                    Log.d(TAG, event.getOrganizer());
                    Log.d(TAG, event.getTitle());
                    Log.d(TAG, event.getPosterCode().toString());
                    Log.d(TAG, event.getDescription());
                    Log.d(TAG, "announcements:");
                    for (String anno : event.getAnnouncement()) {
                        Log.d(TAG, "\t"+anno);
                    }
                }
            }
        });
     */

    public void retrieveEventAttendeeList(String eventID, Admin.OnEventAttendeeListRetrievedListener listener) {
        CollectionReference attendeeListRef = db.collection("EVENT")
                .document(eventID)
                .collection("attendeeList");
        attendeeListRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Attendee> attendees = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()) {
                        Attendee attendee = new Attendee();
                        attendee.setCheckInCount(Objects.requireNonNull(documentSnapshot1.getLong("checkInCount")).intValue());
                        attendee.setName(documentSnapshot1.getString("name"));
                        attendee.setId(documentSnapshot1.getString("id"));
                        attendee.setCheckedIn(documentSnapshot1.getBoolean("checkedIn"));
                        attendees.add(attendee);
                        Log.w(TAG, "attendee added in attendees");
                    }
                    listener.onEventAttendeeListRetrieved(attendees);

                }else {
                    // Handle the case where the task failed
                    Exception e = task.getException();
                    System.out.println("task failed: " + e.getMessage());
                    // Notify the listener with a null user object
                    listener.onEventAttendeeListRetrieved(null);
                }
            }
        });
    }
    /* How to use: (ex: print all attendees' info)
    admin.retrieveEventAttendeeList("20240306001125||1658f5315ca1a74d", new Admin.OnEventAttendeeListRetrievedListener() {
            @Override
            public void onEventAttendeeListRetrieved(ArrayList<Attendee> attendees) {
                for (Attendee attendee : attendees) {
                    Log.d(TAG, "Attendee's user ID: "+attendee.getId());
                    Log.d(TAG, "Attendee's user name: "+attendee.getName());
                    Log.d(TAG, "Attendee's checked-in count: "+String.valueOf(attendee.getCheckInCount()));
                    Log.d(TAG, "is the Attendee checked in now: "+String.valueOf(attendee.isCheckedIn()));
                }
            }
        });
     */


    // Note: this method can not fetch the attendeeList b/c it is in its subcollection, so the attendeeList will be empty?
    // get all the events in the database, which will be stored in an ArrayList
    public void retrieveAllEvent(Admin.OnEventRetrievedListener listener){
        this.query = this.db.collection("EVENT");
        this.query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Event> eventList = new ArrayList<>();
                    //ArrayList<String> organizerIdList = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot0 : task.getResult()) {
                        // read each event
                        if (true) {
                            Log.d(TAG, "Current document: " + documentSnapshot0.getId());
                            Event event = new Event();
                            //OrganizerFireBaseHolder organizer = new OrganizerFireBaseHolder();
                            //retrieve event information
                            event.setId(documentSnapshot0.getString("id"));
                            event.setAnnouncement((ArrayList<String>) documentSnapshot0.get("announcements"));
                            event.setPosterCode(documentSnapshot0.getString("posterCode"));
                            event.setEventCode(documentSnapshot0.getString("eventCode"));
                            event.setTitle(documentSnapshot0.getString("title"));
                            event.setDescription(documentSnapshot0.getString("description"));
                            event.setOrganizer(documentSnapshot0.getString("organizer"));
                            eventList.add(event);
                        }
                        else {
                            Log.d(TAG, "skip...");
                        }
                    }
                    // Notify the listener with the retrieved user object is complete
                    listener.onEventRetrieved(eventList);
                } else {
                    // Handle the case where the task failed
                    Exception e = task.getException();
                    Log.d(TAG, "Query failed: " + e.getMessage());
                    // Notify the listener with a null user object
                    listener.onEventRetrieved(null);
                }
            }
        });
    }
        /* How to use:  // ex: print all events info (except attendeeList)
    note: lines start with "//" won't do anything and you can remove them.
            admin.retrieveAllEvent(new Admin.OnEventRetrievedListener() {
            @Override
            public void onEventRetrieved(ArrayList<Event> events) {

                for (Event event : events) {
                    int counter1 = 0;
//                    int counter2 = 0;
                    counter1++;
                    Log.d(TAG, "Event " + String.valueOf(counter1));
                    Log.d(TAG, event.getId());
                    Log.d(TAG, event.getEventCode());
                    Log.d(TAG, event.getOrganizer());
                    Log.d(TAG, event.getTitle());
                    Log.d(TAG, event.getPosterCode().toString());
                    Log.d(TAG, event.getDescription());
                    Log.d(TAG, "announcements:");
                    for (String anno : event.getAnnouncement()) {
                        Log.d(TAG, "\t" + anno);
                    }
                    402~411 aren't doing anything so I commented them out
//                    Log.d(TAG, "attendees are:");
//                    for (Attendee attendee : event.getAttendees()) {
//                        counter2++;
//                        Log.d(TAG, "Attendee " + String.valueOf(counter2));
//                        Log.d(TAG, attendee.getId());
//                        Log.d(TAG, attendee.getName());
//                        Log.d(TAG, String.valueOf(attendee.getCheckInCount()));
//                        Log.d(TAG, String.valueOf(attendee.isCheckedIn()));
//                    }
//                    counter2 = 0;
                }
            }
        });
     */
    public void retrieveAllUser(Admin.OnUserRetrievedListener listener) {
        this.query = this.db.collection("USER");
        this.query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<User> users = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        //ArrayList<String> attendeeRoles = new ArrayList<String>();
                        //ArrayList<String> organizerRoles = new ArrayList<String>();
                        // Retrieve user data from document and set properties of the User object
                        Log.d(TAG, "Current document (user): "+documentSnapshot.getId());
                        User user = new User();
                        user.setId(documentSnapshot.getString("id"));
                        user.setName(documentSnapshot.getString("name"));
                        user.setProfilePicture(documentSnapshot.getString("profilePicture"));
                        user.setEmail(documentSnapshot.getString("email"));
                        user.setPhone(documentSnapshot.getString("phone"));
                        user.setUniversity(documentSnapshot.getString("university"));
                        user.setAttendeeRoles((ArrayList<String>) documentSnapshot.get("attendeeRoles"));
                        user.setOrganizerRoles((ArrayList<String>) documentSnapshot.get("organizerRoles"));
                        users.add(user);
                    }

                    // Notify the listener with the retrieved user object is complete
                    listener.onUserRetrieved(users);
                } else {
                    // Handle the case where the task failed
                    Exception e = task.getException();
                    System.out.println("Query failed: " + e.getMessage());
                    // Notify the listener with a null user object
                    listener.onUserRetrieved(null);
                }
            }
        });
    }
    /* How to use (ex: print info of all users)
    admin.retrieveAllUser(new Admin.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(ArrayList<User> users) {
                if (users == null) {
                    Log.w(TAG, "ArrayList<User> users is null");
                    return;
                }
                int counter =0;
                for (User user : users) {
                    counter = counter + 1;
                    Log.d(TAG, "User "+counter);
                    Log.d(TAG, "\tUser id: "+user.getId());
                    Log.d(TAG, "\tUser name: "+user.getName());
                    Log.d(TAG, "\tUser email: "+user.getEmail());
                    Log.d(TAG, "\tUser phone: "+user.getPhone());
                    Log.d(TAG, "\tUser university: "+user.getUniversity());
                    Log.d(TAG, "\tUser avatar URL: "+user.getProfilePicture());
                    Log.d(TAG, "\tUser's signed events' id: ");
                    for (String signedEventID : user.getAttendeeRoles()) {
                        Log.d(TAG, "\t\tEvent id: "+signedEventID);
                    }
                    Log.d(TAG, "\tUser's hosting events' id: ");
                    for (String hostingEventID : user.getOrganizerRoles()) {
                        Log.d(TAG, "\t\tEvent id: "+hostingEventID);
                    }
                    Log.d(TAG, "\n");
                }
            }
        });
     */

}
