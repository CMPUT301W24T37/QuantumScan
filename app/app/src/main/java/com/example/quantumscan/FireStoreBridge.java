package com.example.quantumscan;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FireStoreBridge implements OrganizerCreateEvent.imageUrlUploadListener{
    private FirebaseFirestore db;
    private CollectionReference collectionName;
    private String c;
    private Query query;
    private FirebaseStorage storage;

    public FireStoreBridge(String collectionName){
        c = collectionName;
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
    interface OnCheckedInListener{
        void onCheckedInListener(ArrayList<AttendeeFireBaseHolder> attendeeList);
    }

    public interface OnUserCheckInListener{
        void onCheckUserJoin(boolean attendeeExist);
    }

    public interface OnRetrieveAnnouncement{
        void onRetrieveAnnouncement(Announcement announcement);
    }
    public interface OnRetrieveEventAnnouncement{
        void onRetrieveEventAnnouncement (ArrayList<String> announcements);
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
        CollectionReference userCollection = getDb().collection("USER");
        DocumentReference user = userCollection.document(userID);
        user.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                try  {
                        User user = new User(null,null,null, null,null);
                        ArrayList<String> attendeeRoles = new ArrayList<String>();
                        ArrayList<String> organizerRoles = new ArrayList<String>();

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


                    // Notify the listener with the retrieved user object is complete
                    listener.onUserRetrieved(user, attendeeRoles, organizerRoles);
                } catch (Exception e){

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
                                attendee.setCheckInCount(Objects.requireNonNull(documentSnapshot1.getLong("checkInCount")).intValue());
                                attendee.setName(documentSnapshot1.getString("name"));
                                attendee.setId(documentSnapshot1.getString("id"));
                                attendee.setCheckedIn(documentSnapshot1.getBoolean("checkedIn"));
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
    public void createUser(UserFireBaseHolder user){
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
     * @param user a interface that contain retrieved data which is stored in a firebase holder*
     * */
    public void updateUser(UserFireBaseHolder user){
        String userID = user.getId();
        this.collectionName.document(userID).update("name", user.getName());
        this.collectionName.document(userID).update("university", user.getUniversity());
        this.collectionName.document(userID).update("phone", user.getPhone());
        this.collectionName.document(userID).update("email", user.getEmail());
    }

    public void updateProfilePhoto(String userId, String profilePhoto){
        this.collectionName.document(userId).update("profilePicture", profilePhoto);
    }
    public void deleteProfilePhoto(String userId, String profilePhoto) {
        this.collectionName.document(userId).update("profilePicture", profilePhoto);
        StorageReference desertRef = storage.getReference().child("default_avatars/" + userId + ".jpg");
        desertRef.delete();
    }

    /** updateEventHelper will take in a eventID and a organizerID. organizerID will be used to identify
     user in USER collection. eventID will be added into organizerRoles filed to keep track which event
     are organized by the user
     @param eventInfo {@link Event}
     @param organizerID {@link String}
     **/
    public void updateEvent(Event eventInfo, String organizerID, String startTime, String endTime){
        // get event id
        String eventId= eventInfo.getId();
        CollectionReference eventCollection = getDb().collection("EVENT");

        EventFireBaseHolder event = new EventFireBaseHolder(
                eventInfo.getAnnouncement(),
                eventInfo.getDescription(),
                eventInfo.getEventCode(),
                eventId,
                organizerID,
                eventInfo.getPosterCode(),
                eventInfo.getTitle(),
                eventInfo.getAttendeeLimit(),
                eventInfo.getCurrentTotalAttendee());

        this.updateEventHelper(eventId, organizerID);
        eventCollection.document(eventId).set(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        updateStartTime(eventId, startTime);
                        updateEndTime(eventId, endTime);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("event upload failed");
                    }
                });



    }

    /** updateEventHelper will take in a eventID and a organizerID. organizerID will be used to identify
     user in USER collection. eventID will be added into organizerRoles filed to keep track which event
     are organized by the user
     @param eventID {@link String}
     @param organizerID {@link String}
     **/
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

    public void updateImage(String EventID, ImageView imageView, Uri imageUri){
        StorageReference desertRef = storage.getReference().child(EventID+"jpg");
        desertRef.delete();
        StorageReference imageRef = storage.getReference().child(EventID + ".jpg");
        imageRef.putFile(imageUri);
        //displayImage(EventID, imageView);

    }

    public void updatePhoto(String userID, Uri imageUri) {
        StorageReference desertRef = storage.getReference().child("default_avatars/" + userID + ".jpg");
        desertRef.delete();
        StorageReference imageRef = storage.getReference().child("default_avatars/" + userID + ".jpg");
        imageRef.putFile(imageUri);
        this.collectionName.document(userID).update("profilePicture", userID + ".jpg");
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

    public void displayProfile(String pictureName, ImageView imageView){
        StorageReference islandRef = this.storage.getReference().child("default_avatars/"+pictureName);

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

    public void updateEventAnnouncement(String eventId,String announcement){
        CollectionReference eventCollection = getDb().collection("EVENT");
        DocumentReference eventDoc = eventCollection.document(eventId);
        eventDoc.update("announcements", FieldValue.arrayUnion(announcement))
                .addOnSuccessListener(aVoid -> System.out.println("Array updated successfully."))
                .addOnFailureListener(e -> System.err.println("Error updating array: " + e.getMessage()));
    }

    /**
     * find user in a database:
     * <p>
     * This method is responsible for retrieving user information given the user id.
     * user id should be directly obtained from the database or from the device
     * </p>
     * */
    public void updateAttendeeCheckIn(String userId, String eventId){
        CollectionReference EventCollection = getDb().collection("EVENT");
        System.out.println("chekd in fb" + userId);

        System.out.println("chekd in fb" + eventId);

        EventCollection.document(eventId).collection("attendeeList").document(userId).update("checkedIn", true)
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

        EventCollection.document(eventId).collection("attendeeList").document(userId).update("checkInCount", FieldValue.increment(1))
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

    public void updateAttendeeCheckInWithLocation(String userId, String eventId, Location location){
        CollectionReference EventCollection = getDb().collection("EVENT");
        System.out.println("chekd in fb" + userId);

        System.out.println("chekd in fb" + eventId);

        EventCollection.document(eventId).collection("attendeeList").document(userId).update("checkedIn", true)
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

        EventCollection.document(eventId).collection("attendeeList").document(userId).update("checkInCount", FieldValue.increment(1))
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
        updateAttendeeLocation( userId,  eventId,  location);

    }

    public void updateAttendeeLocation(String userId, String eventId, Location location) {
        CollectionReference EventCollection = getDb().collection("EVENT");
        GeoPoint geopoint = null;
        if (location != null) {
            geopoint = new GeoPoint(location.getLatitude(), location.getLongitude());
            Log.d(TAG, "successfully get the location");
        }
        else {Log.w(TAG, "the location is null (will update as null)");}
        EventCollection.document(eventId).collection("attendeeList").document(userId).update("location", geopoint)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Geopoint location updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Geopoint location updated failed");
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
        CollectionReference collection = getDb().collection("EVENT");
        CollectionReference attendeeList = collection.document(eventId).collection("attendeeList");

            attendeeList.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    try {
                        ArrayList<AttendeeFireBaseHolder> attendeeList = new ArrayList<>();

                        int count = 0;
                        for (QueryDocumentSnapshot document : value) {
                            AttendeeFireBaseHolder attendee = new AttendeeFireBaseHolder();
                            attendee.setCheckInCount(document.getLong("checkInCount").intValue());
                            attendee.setName(document.getString("name"));
                            attendee.setId(document.getId());
                            attendee.setCheckedIn(document.getBoolean("checkedIn"));
                            attendeeList.add(attendee);
                            count++;
                        }


                        listener.onCheckedInListener(attendeeList);
                    } catch (Exception e){
                        System.out.println(error);
                        listener.onCheckedInListener(null);
                    }
                }
            }
        );
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
                    long currentTotalAttendee = documentSnapshot.getLong("currentTotalAttendee" );
                    if (currentTotalAttendee < attendeeLimit){
                        System.out.println("before increment");
                        newCollection.document(eventId).update("currentTotalAttendee",FieldValue.increment(1));
                        System.out.println("after incremenet");
                        updateAttendeeSignUpHelper(userId, eventId);

                    }else{
                        System.out.println("you have reached limit");

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

    public void updateAttendeeSignUpHelper(String userId, String eventId){
        CollectionReference eventCollection =  getDb().collection("EVENT");
        CollectionReference userCollection =  getDb().collection("USER");


        Query newQuery = userCollection.whereEqualTo(FieldPath.documentId(), userId);

        newQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            AttendeeFireBaseHolder attendee = new AttendeeFireBaseHolder();
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (task.isSuccessful()) {
                        attendee.setName(document.getString("name"));
                        attendee.setId(userId);
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

    public void checkAttendeeExist(String userId, String eventId, OnUserCheckInListener listener){
        System.out.println("before crash," + eventId);
        CollectionReference attendeeListCollection = getDb().collection("EVENT").document(eventId).collection("attendeeList");

        // Query for documents in the 'attendeeList' collection where the document ID matches 'userId'
        attendeeListCollection.whereEqualTo(FieldPath.documentId(), userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    // Document with 'userId' exists in the 'attendeeList' collection
                    System.out.println("Document exists");
                    listener.onCheckUserJoin(true);
                } else {
                    // No document with 'userId' in the 'attendeeList' collection
                    System.out.println("Document doesn't exist");
                    listener.onCheckUserJoin(false);
                }
            } else {
                // Handle the error
                System.out.println("Error checking document existence");
                // Optionally, call the listener with an error or false
            }
        });
    }

    public void retrieveAnnouncement(String userId, OnRetrieveAnnouncement listener){
        CollectionReference collectionUser = getDb().collection("USER");
        // Change from get().addOnCompleteListener to addSnapshotListener for real-time updates
        DocumentReference userDocRef = collectionUser.document(userId);
        userDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {


            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                System.out.println("changed detected 1");
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    List<String> eventIdList = (List<String>) documentSnapshot.get("attendeeRoles");

                    for (String docId : eventIdList) {
                        DocumentReference docRef = getDb().collection("EVENT").document(docId);
                        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.w(TAG, "Event listen failed.", e);
                                    return;
                                }

                                if (documentSnapshot != null && documentSnapshot.exists()) {
                                    String organizer = documentSnapshot.getString("organizer");
                                    List<String> announcementList = (List<String>) documentSnapshot.get("announcements");
                                    String eventTitle = documentSnapshot.getString("title");
                                    // Ensure there is at least one announcement to retrieve
                                    if (announcementList != null && !announcementList.isEmpty()) {
                                        String announcement = announcementList.get(announcementList.size() - 1);
                                        Announcement annouuncement = new Announcement(organizer, announcement, eventTitle);
                                        System.out.println("changed detected 2");
                                        listener.onRetrieveAnnouncement(annouuncement);
                                    }
                                } else {
                                    Log.d(TAG, "Event data: null");
                                    System.out.println("changed detected 3");
                                    listener.onRetrieveAnnouncement(null);
                                }
                            }
                        });
                    }
                } else {
                    Log.d(TAG, "User data: null");
                    System.out.println("changed detected 4");
                    listener.onRetrieveAnnouncement(null);
                }
                listener.onRetrieveAnnouncement(null);
                System.out.println("changed detected 5");
            }
        });
    }


    public interface OnRetrieveJoinedEvent{
        void onRetrieveJoinedEvent(ArrayList<EventFireBaseHolder> eventList, ArrayList<String> startTime, ArrayList<String> endTime);
    }

    public void retrieveJoinedEvent(String userID, OnRetrieveJoinedEvent listener) {
        System.out.println("triggered" + " 23");
        CollectionReference collectionUser = getDb().collection("USER");
        DocumentReference userDoc = collectionUser.document(userID);
        userDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                System.out.println("triggered" + " 23.5");
                System.out.println(userID);
                CollectionReference collectionEvent = getDb().collection("EVENT");
                List<String> eventIdList = (List<String>) value.get("attendeeRoles");
                System.out.println("important size "+ eventIdList.size());
                Map<String, EventFireBaseHolder> eventMap = new HashMap<>();
                AtomicInteger remainingEvents = new AtomicInteger(eventIdList.size());
                ArrayList<String> startTime = new ArrayList<>();
                ArrayList<String> endTime = new ArrayList<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

                for (String eventId : eventIdList) {
                    collectionEvent.document(eventId).get().addOnSuccessListener(documentSnapshot -> {

                        EventFireBaseHolder event = new EventFireBaseHolder();
                        System.out.println(eventId);
                        System.out.println("_----------------------_");

                        startTime.add(dateFormat.format(documentSnapshot.getTimestamp("startTime").toDate()));
                        endTime.add(dateFormat.format(documentSnapshot.getTimestamp("endTime").toDate()));
                        event.setEventCode(documentSnapshot.getString("eventCode"));
                        event.setOrganizer(documentSnapshot.getString("organizer"));
                        event.setDescription(documentSnapshot.getString("description"));
                        event.setTitle(documentSnapshot.getString("title"));
                        event.setAttendeeLimit(documentSnapshot.getLong("attendeeLimit"));
                        event.setCurrentTotalAttendee(documentSnapshot.getLong("currentTotalAttendee"));
                        event.setPosterCode(documentSnapshot.getString("organizer")); // Should this be "posterCode" instead of "organizer"?
                        event.setId(documentSnapshot.getId()); // Use documentSnapshot.getId() to ensure the ID is accurately captured


                        synchronized (eventMap) {
                            eventMap.put(event.getId(), event);
                        }

                        // Check if all events have been fetched
                        if (remainingEvents.decrementAndGet() == 0) {
                            System.out.println("size size size size size + " + eventMap.size());
                            // Convert map values to a list to match the listener's expected input
                            ArrayList<EventFireBaseHolder> uniqueEventList = new ArrayList<>(eventMap.values());
                            System.out.println("size size size size size + 2" + uniqueEventList.size());
                            listener.onRetrieveJoinedEvent(uniqueEventList, startTime, endTime);
                        }
                    }).addOnFailureListener(e -> {
                        Log.e(TAG, "Error fetching event document", e);
                        if (remainingEvents.decrementAndGet() == 0) {
                            ArrayList<EventFireBaseHolder> uniqueEventList = new ArrayList<>(eventMap.values());
                            listener.onRetrieveJoinedEvent(uniqueEventList, startTime, endTime); // Or handle error accordingly
                        }
                    });
                }
            }
        });
    }


    public void retrieveEventAnnouncement(String eventId, OnRetrieveEventAnnouncement listener){
        CollectionReference collectionUser = getDb().collection("EVENT");
        // Change from get().addOnCompleteListener to addSnapshotListener for real-time updates
        DocumentReference userDocRef = collectionUser.document(eventId);
        userDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                System.out.println("triggered");
                ArrayList<String> announcementList = (ArrayList<String>)  documentSnapshot.get("announcements");
                listener.onRetrieveEventAnnouncement(announcementList);
                // Ensure there is at least one announcement to retrieve
            }

        });
    }


    public void retrieveOrganizedEvent(String userID, OnRetrieveJoinedEvent listener) {
        System.out.println("triggered" + " 23");
        CollectionReference collectionUser = getDb().collection("USER");
        DocumentReference userDoc = collectionUser.document(userID);
        userDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                System.out.println("triggered" + " 23.5");
                System.out.println(userID);
                CollectionReference collectionEvent = getDb().collection("EVENT");

                List<String> eventIdList = (List<String>) value.get("organizerRoles");
                if (eventIdList == null) {
                    return;
                }
                //System.out.println("important size "+ eventIdList.size());
                Map<String, EventFireBaseHolder> eventMap = new HashMap<>();
                AtomicInteger remainingEvents = new AtomicInteger(eventIdList.size());
                ArrayList<String> startTime = new ArrayList<>();
                ArrayList<String> endTime = new ArrayList<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                for (String eventId : eventIdList) {
                    collectionEvent.document(eventId).get().addOnSuccessListener(documentSnapshot -> {

                        EventFireBaseHolder event = new EventFireBaseHolder();


                        //startTime.add(dateFormat.format(documentSnapshot.getTimestamp("startTime").toDate()));
                       // endTime.add(dateFormat.format(documentSnapshot.getTimestamp("endTime").toDate()));
                        event.setEventCode(documentSnapshot.getString("eventCode"));
                        event.setOrganizer(documentSnapshot.getString("organizer"));
                        event.setDescription(documentSnapshot.getString("description"));
                        event.setTitle(documentSnapshot.getString("title"));
                        event.setAttendeeLimit(documentSnapshot.getLong("attendeeLimit"));
                        event.setCurrentTotalAttendee(documentSnapshot.getLong("currentTotalAttendee"));
                        event.setPosterCode(documentSnapshot.getString("organizer")); // Should this be "posterCode" instead of "organizer"?
                        event.setId(documentSnapshot.getId()); // Use documentSnapshot.getId() to ensure the ID is accurately captured

                        synchronized (eventMap) {
                            eventMap.put(event.getId(), event);
                        }

                        // Check if all events have been fetched
                        if (remainingEvents.decrementAndGet() == 0) {
                            System.out.println("size size size size size + " + eventMap.size());
                            // Convert map values to a list to match the listener's expected input
                            ArrayList<EventFireBaseHolder> uniqueEventList = new ArrayList<>(eventMap.values());
                            System.out.println("size size size size size + 2" + uniqueEventList.size());
                            listener.onRetrieveJoinedEvent(uniqueEventList, startTime, endTime);
                        }
                    }).addOnFailureListener(e -> {
                        Log.e(TAG, "Error fetching event document", e);
                        if (remainingEvents.decrementAndGet() == 0) {
                            ArrayList<EventFireBaseHolder> uniqueEventList = new ArrayList<>(eventMap.values());
                            listener.onRetrieveJoinedEvent(uniqueEventList, startTime, endTime); // Or handle error accordingly
                        }
                    });
                }
            }
        });
    }

    private void updateStartTime(String eventId, String startTime){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        CollectionReference eventReference = getDb().collection("EVENT");
        DocumentReference eventDoc = eventReference.document(eventId);
        try {
            Date date = sdf.parse(startTime);

            Timestamp timestamp = new Timestamp(date);

            eventDoc.update("startTime", timestamp)
                    .addOnSuccessListener(aVoid -> System.out.println("Event start time updated successfully"))
                    .addOnFailureListener(e -> System.err.println("Error updating event start time: " + e.getMessage()));

        } catch (ParseException e) {
            System.err.println("Failed to parse date: " + e.getMessage());
        }

    }
    private void updateEndTime(String eventId, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        CollectionReference eventReference = getDb().collection("EVENT");
        DocumentReference eventDoc = eventReference.document(eventId);
        try {
            Date date = sdf.parse(endTime);

            Timestamp timestamp = new Timestamp(date);

            eventDoc.update("endTime", timestamp)
                    .addOnSuccessListener(aVoid -> System.out.println("Event start time updated successfully"))
                    .addOnFailureListener(e -> System.err.println("Error updating event start time: " + e.getMessage()));

        } catch (ParseException e) {
            System.err.println("Failed to parse date: " + e.getMessage());
        }
    }
}
