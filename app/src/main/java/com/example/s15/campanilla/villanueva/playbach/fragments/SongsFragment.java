package com.example.s15.campanilla.villanueva.playbach.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s15.campanilla.villanueva.playbach.AddSong;
import com.example.s15.campanilla.villanueva.playbach.Classes.Songs;
import com.example.s15.campanilla.villanueva.playbach.DBManager;
import com.example.s15.campanilla.villanueva.playbach.R;
import com.example.s15.campanilla.villanueva.playbach.SongsAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class SongsFragment extends Fragment {
    private RecyclerView songsRecyclerView;
    private SongsAdapter songsAdapter;
    private List<Songs> songsList = new ArrayList<>();
    public SongsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        View addSongButton = view.findViewById(R.id.addSong);
        addSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddSong.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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