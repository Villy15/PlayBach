package com.example.s15.campanilla.villanueva.playbach.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.s15.campanilla.villanueva.playbach.Classes.Songs;
import com.example.s15.campanilla.villanueva.playbach.DBManager;
import com.example.s15.campanilla.villanueva.playbach.LoginActivity;
import com.example.s15.campanilla.villanueva.playbach.R;
import com.example.s15.campanilla.villanueva.playbach.SongsAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    //private FirebaseAuth firebaseAuth;




//        FirebaseApp.initializeApp(requireContext());
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        View logoutButton = view.findViewById(R.id.logout);
//
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut();
//
//                Intent intent = new Intent(requireActivity(), LoginActivity.class);
//                startActivity(intent);
//
//                Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show();
//            }
//        });

    Button logoutButton;

    TextView textViewDisplayName;

    private RecyclerView songsRecyclerView;
    private SongsAdapter songsAdapter;
    private List<Songs> songsList = new ArrayList<>();

    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logoutButton = view.findViewById(R.id.logoutButton);
        textViewDisplayName = view.findViewById(R.id.textView3);
        ImageView imageViewProfilePicture = view.findViewById(R.id.imageView2);

        // Get the current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            textViewDisplayName.setText(displayName);

            if (currentUser != null && currentUser.getPhotoUrl() != null) {
                // Get the original photo URL
                String originalPhotoUrl = currentUser.getPhotoUrl().toString();

                // Changes higher res
                String highResPhotoUrl = originalPhotoUrl.replace("s96-c", "s512-c"); // For example, requesting a 512x512 image

                Glide.with(this)
                        .load(highResPhotoUrl)
                        .into(imageViewProfilePicture);
            }
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log out the current user
                FirebaseAuth.getInstance().signOut();

                // Google sign out
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);
                googleSignInClient.signOut().addOnCompleteListener(getActivity(), task -> {
                    // Go to LoginActivity after signing out
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                    startActivity(intent);
                });

                // Go to LoginActivity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                startActivity(intent);
            }
        });

        songsRecyclerView = view.findViewById(R.id.songsRecyclerView);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        populateSongsList();

        songsAdapter = new SongsAdapter(songsList);
        songsRecyclerView.setAdapter(songsAdapter);
    }

    private void populateSongsList() {
        DBManager dbManager = new DBManager();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String displayName = user.getDisplayName();

            dbManager.getAllSongsByUser(displayName, new OnSuccessListener<List<Songs>>() {
                @Override
                public void onSuccess(List<Songs> songs) {
                    songsList.clear(); // Clear existing songs
                    songsList.addAll(songs); // Add all songs from Firestore
                    songsAdapter.notifyDataSetChanged(); // Notify the adapter
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("TAG", "Error getting documents: ", e);
                }
            });
        } else {
            // Handle case where user is null
            Log.w("TAG", "User is not logged in");
        }
    }
}
