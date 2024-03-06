
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

public class FireStoreBridge {
    private FirebaseFirestore db;
    private CollectionReference collectionName;
    private Query query;

    public FireStoreBridge(String collectionName){
        this.db = FirebaseFirestore.getInstance();
        this.collectionName = this.db.collection(collectionName);
        this.query = this.collectionName;

    }
    public interface OnUserRetrievedListener {
        void onUserRetrieved(User user);
    }

    public interface OnEventRetrievedListener {
        void onEventRetrieved(User user);
    }


    public void retrieveUser(String userID, OnUserRetrievedListener listener) {
        this.query = this.collectionName.whereEqualTo(FieldPath.documentId(), userID);

        this.query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    User user = new User(null,null,null, null,null); // Create a new User object
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        // Retrieve user data from document and set properties of the User object
                        user.setName(documentSnapshot.getString("Name"));
                        user.setProfilePicture(documentSnapshot.getString("profilePicture"));
                        user.setPhone(documentSnapshot.getString("phone"));
                        user.setUniversity(documentSnapshot.getString("university"));
                        user.setEmail(documentSnapshot.getString("email"));

                    }
                    // Notify the listener with the retrieved user object is complete
                    listener.onUserRetrieved(user);
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
    public void retrieveEvent(String eventID, OnEventRetrievedListener listener) {
        this.query = this.collectionName.whereEqualTo(FieldPath.documentId(), eventID);

        this.query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Event event = new Event();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    }
                    // Notify the listener with the retrieved user object is complete
                    listener.onEventRetrieved(null);
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


    public boolean updateEvent(String eventID, Event event){
        return false;
    }



}

