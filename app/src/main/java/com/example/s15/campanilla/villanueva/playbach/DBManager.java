package com.example.s15.campanilla.villanueva.playbach;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.s15.campanilla.villanueva.playbach.Classes.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DBManager {
    private static final String TAG = "DBManager";
    private final FirebaseFirestore db;

    public DBManager() {
        db = FirebaseFirestore.getInstance();
    }

    public void addUser(Users user) {
        db.collection("users") // Replace with your collection name if different
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
