package com.example.s15.campanilla.villanueva.playbach.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s15.campanilla.villanueva.playbach.AddSong;
import com.example.s15.campanilla.villanueva.playbach.Classes.Songs;
import com.example.s15.campanilla.villanueva.playbach.R;
import com.example.s15.campanilla.villanueva.playbach.SongsAdapter;

import java.util.ArrayList;
import java.util.List;


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

    private void populateSongsList () {
        songsList.add(new Songs(
                "Littleroot Town - Pokemon",
                "vbLu5O8rxQk",
                "https://img.youtube.com/vi/vbLu5O8rxQk/0.jpg",
                "Adrian Villanueva"
            )
        );

        songsList.add(new Songs(
                "Persona 5 - Life Will Change (中英歌詞)",
                "CGwH6rZk7VM",
                "https://img.youtube.com/vi/CGwH6rZk7VM/0.jpg",
                "Adrian Villanueva"
            )
        );

        songsList.add(new Songs(
                "I Really Want to Stay At Your House",
                "h4VJGNNSQnw",
                "https://img.youtube.com/vi/h4VJGNNSQnw/0.jpg",
                "Adrian Villanueva"
            )
        );
    }
}