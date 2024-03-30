package com.example.quantumscan;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FireStoreBridge implements OrganizerCreateEvent.imageUrlUploadListener{
    private FirebaseFirestore db;
    private CollectionReference collectionName;
    private Query query;
    private FirebaseStorage storage;

    public FireStoreBridge(String collectionName){
        this.db = FirebaseFirestore.getInstance();
        this.collectionName = this.db.collection(collectionName);
        this.query = this.collectionName;
        this.storage = FirebaseStorage.getInstance();
    }
    public FirebaseFirestore getDb() {
        return db;
    }

    public CollectionReference getCollectionName() {
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
    private interface OnCheckedInListener{
        void onCheckedInListener(ArrayList<AttendeeFireBaseHolder> attendeeList);
    }
    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * @param listener a interface that contain retrieved data which is stored in a firebase holder
     * @param userID the height of the rectangle, must be non-negative
     */
    public void retrieveUser(String userID, OnUserRetrievedListener listener) {
        this.query = this.collectionName.whereEqualTo(FieldPath.documentId(), userID);
        this.query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    User user = new User(null,null,null, null,null);
                    ArrayList<String> attendeeRoles = new ArrayList<String>();
                    ArrayList<String> organizerRoles = new ArrayList<String>();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        // Retrieve user data from document and set properties of the User object
                        user.setName(documentSnapshot.getString("name"));
                        user.setProfilePicture(documentSnapshot.getString("profilePicture"));
                        user.setPhone(documentSnapshot.getString("phone"));
                        user.setUniversity(documentSnapshot.getString("university"));
                        user.setEmail(documentSnapshot.getString("email"));
                        user.setId(documentSnapshot.getString("id"));
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

    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * @param listener a interface that contain retrieved data which is stored in a firebase holder
     * @param eventId the height of the rectangle, must be non-negative
     */

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

    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * @param listener a interface that contain retrieved data which is stored in a firebase holder
     * */
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

    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * @param attendeeListRef a interface that contain retrieved data which is stored in a firebase holder
     * @param organizer a interface that contain retrieved data which is stored in a firebase holder
     *
     * */
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

    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * @param user a interface that contain retrieved data which is stored in a firebase holder*
     * */
    public void updateUser(UserFireBaseHolder user){
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

    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * */
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
                    eventInfo.getAttendees().get(i).getCheckInCount());
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
        this.updateEventHelper(eventId, organizerID);
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



    }

    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * */
    private void updateEventHelper(String eventID, String organizerID){
        CollectionReference userCollection = this.getDb().collection("USER");
        userCollection.document(organizerID).update("organizerRoles", FieldValue.arrayUnion(eventID))
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
                });;

    }

    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * */
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

    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * */
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

    @Override
    public void uploadEventImage(Event newEvent, String evenID, Uri imageUri) {
        StorageReference imageRef = storage.getReference().child(newEvent.getId() + ".jpg");
        imageRef.putFile(imageUri);
    }

    public void displayImage(String EventID, ImageView imageView){
        StorageReference islandRef = this.storage.getReference().child(EventID+".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * */
    public void updateAttendeeCheckIn(String userId, String eventId){
        this.collectionName.document(eventId).collection("attendeeList").document(userId).update("checkedIn", true)
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

        this.collectionName.document(eventId).collection("attendeeList").document(userId).update("checkInCount", FieldValue.increment(1))
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

    }


    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * */
    public void retrieveAttendeeCheckIn(String eventId, OnCheckedInListener listener){
        this.query = this.collectionName.document(eventId).collection("attendeeList");
        this.query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<AttendeeFireBaseHolder> attendeeList = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        AttendeeFireBaseHolder attendee = new AttendeeFireBaseHolder();
                        attendee.setCheckInCount(document.getLong("checkInCount"));
                        attendee.setName(document.getString("name"));
                        attendee.setAttendeeId(document.getId());
                        attendee.setCheckInStatus(document.getBoolean("checkedIn"));
                        attendeeList.add(attendee);
                    }
                } else {

                }
                listener.onCheckedInListener(attendeeList);
            }

        });


    }

    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * */


    public void updateAttendeeSignUpToEvent(String userId, String eventId){
        CollectionReference newCollection =  getDb().collection("EVENT");
        Query newQuery;
        newQuery = newCollection.whereEqualTo(FieldPath.documentId(), eventId);
        newQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    long attendeeLimit = documentSnapshot.getLong("attendeeLimit");
                    long currentTotalAttendee = documentSnapshot.getLong("currentTotalAttendee");
                    if (currentTotalAttendee < attendeeLimit){

                        updateAttendeeSignUpHelper(userId, eventId);
                    }else{

                    }
                }

            }
        });

    }

    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * */

    private void updateAttendeeSignUpHelper(String userId, String eventId){
        CollectionReference eventCollection =  getDb().collection("EVENT");
        CollectionReference userCollection =  getDb().collection("USER");
        System.out.println(userId+"1234567890");

        Query newQuery = userCollection.whereEqualTo(FieldPath.documentId(), userId);

        newQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            AttendeeFireBaseHolder attendee = new AttendeeFireBaseHolder();
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (task.isSuccessful()) {
                        attendee.setName(document.getString("name"));
                        eventCollection.document(eventId).collection("attendeeList").document(userId).set(attendee)
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

                        userCollection.document(userId).update("attendeeRoles", FieldValue.arrayUnion(eventId))
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
                    }else{

                    }

                }
            }
        });

    }


}
