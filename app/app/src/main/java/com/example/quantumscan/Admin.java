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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Admin {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private CollectionReference collectionName;
    //private Query query;
    //private FirebaseStorage storage;
    //private FireStoreBridge fbUser = new FireStoreBridge("USER");
    //private FireStoreBridge fbEvent = new FireStoreBridge("EVENT");
    //boolean result;

    public Admin() {
    }

//    public interface OnDocumentsDeletedListener {
//        void onDocumentsDeleted(String eventID);
//    }

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
                        Log.d(TAG, "Error getting documents: ", task.getException());
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

//    private Event getEvent(String eventID) {
//        //Event event;
//        fbEvent.retrieveEvent(eventID,new FireStoreBridge.OnEventRetrievedListener() {
//            @Override
//            public void onEventRetrieved(ArrayList<Event> event1, ArrayList<String> organizerList) {
//                Event event = event1.get(0);
//            }
//        });
//        event;
//    }

//    public boolean removeEvent(String eventID) {
//        try {
//            // 1. Check if the event exists
//            DocumentReference eventRef = db.collection("EVENT").document(eventID);
//            DocumentSnapshot eventSnapshot = eventRef.get().getResult();
//
//            if (!eventSnapshot.exists()) {
//                // 1E. Event does not exist
//                return false;
//            }
//
//            // attendees (todo)
//            List<String> attendeeIds = (List<String>) eventRef.collection("attendeeList").get();
//            // organizer
//            String organizerId = eventSnapshot.getString("organizerId");
//
//            // 2. Loop through Attendee user id list and remove the event ID from their attendeeRoles list
//            for (String userId : attendeeIds) {
//                DocumentReference userRef = db.collection("users").document(userId);
//                // Example update, depends on your data structure
//                // This might involve more complex logic to actually remove the eventId from the list
//                userRef.update("attendeeRoles", FieldValue.arrayRemove(eventID));
//            }
//
//            // 3. Remove the event ID from the organizer's organizerRoles list
//            DocumentReference organizerRef = db.collection("users").document(organizerId);
//            organizerRef.update("organizerRoles", FieldValue.arrayRemove(eventID));
//
//            // 4. Delete the event document from Firebase
//            eventRef.delete().get();
//
//            // 5. Return true, indicating success
//            return true;
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//            // E. If any errors occur, return false
//            return false;
//        }
//    }

    private void deleteEventHelper(String eventID) {
        // 4.01 delete the sub-collection of the event
        SystemClock.sleep(1500);
        this.deleteAttendeeListSubCollection(eventID);
        // 4 delete event
//        SystemClock.sleep(1500);
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
    // Todo: meaning the return value won't work which may lead to unexpected results
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
        SystemClock.sleep(1500);
        this.deleteEventHelper(eventID);
    }
}
