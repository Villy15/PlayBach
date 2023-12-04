package com.example.s15.campanilla.villanueva.playbach;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.s15.campanilla.villanueva.playbach.Classes.Songs;
import com.example.s15.campanilla.villanueva.playbach.Classes.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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

    public Task<Boolean> checkUserExists(String email) {
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("users") // Replace with your collection name
                .whereEqualTo("email", email) // Assuming 'email' is the field name in your Firestore
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            taskCompletionSource.setResult(true);
                        } else {
                            taskCompletionSource.setResult(false);
                        }
                    } else {
                        Log.e(TAG, "Error checking if user exists", task.getException());
                        taskCompletionSource.setException(task.getException());
                    }
                });

        return taskCompletionSource.getTask();
    }

    public void addSong(String title, String youtubeUrl, String thumbnail, String contributor) {
        // Create a Songs object with the provided information
        Songs song = new Songs(title, youtubeUrl, thumbnail, contributor);

        db.collection("songs")
                .add(song)
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
