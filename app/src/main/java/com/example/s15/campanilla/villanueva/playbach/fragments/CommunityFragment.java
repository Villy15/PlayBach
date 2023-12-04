package com.example.s15.campanilla.villanueva.playbach.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s15.campanilla.villanueva.playbach.Classes.Songs;
import com.example.s15.campanilla.villanueva.playbach.CommunityAdapter;
import com.example.s15.campanilla.villanueva.playbach.R;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {
    private RecyclerView songsRecyclerView;
    private CommunityAdapter communityAdapter;
    private List<Songs> songsList = new ArrayList<>();


    public CommunityFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        songsRecyclerView = view.findViewById(R.id.songsRecyclerView);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        populateSongsList();

        communityAdapter = new CommunityAdapter(songsList);
        songsRecyclerView.setAdapter(communityAdapter);
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