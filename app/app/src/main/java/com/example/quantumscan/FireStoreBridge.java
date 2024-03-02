package com.example.authtest;

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

    public boolean retrieveUser(String userID){
        this.query.whereEqualTo(FieldPath.documentId(), userID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // The query was successful, process the QuerySnapshot
                            QuerySnapshot querySnapshot = task.getResult();
                            if (!querySnapshot.isEmpty()) {
                                // Iterate through the documents in the QuerySnapshot
                                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                                    // Print the document ID and the data in the document
                                    // TODO: reformat output data
                                    System.out.println("Document ID: " + documentSnapshot.getId());
                                    System.out.println("Document Data: " + documentSnapshot.getData());
                                }
                            } else {
                                // Handle the case where there are no documents in the QuerySnapshot
                                System.out.println("No documents matched the query.");
                            }
                        } else {
                            // The task failed, handle the error
                            Exception e = task.getException();
                            System.out.println("Query failed: " + e.getMessage());
                        }
                    }
                });
        return false;
    }

    public boolean retrieveEvent(String eventID, ArrayList<Event> eventList){
       return false;
    }

    public boolean updateUser(String userID, User user){
        /*
        Responsible for both creating and updating user information
         */
        this.collectionName.document(userID).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("upload failed");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("upload success");
                    }
                });

        return false;
    }


    public boolean updateEvent(String eventID, Event event){
        return false;
    }


}

