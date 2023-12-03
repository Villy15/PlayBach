package com.example.s15.campanilla.villanueva.playbach;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.s15.campanilla.villanueva.playbach.Classes.Songs;
import com.example.s15.campanilla.villanueva.playbach.fragments.HomeFragment;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder>{
    private static final String PREFS_NAME = "SongsAdapterPreferences";

    private List<Songs> songsList;

    public SongsAdapter(List<Songs> songsList) {
        this.songsList = songsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Songs song = songsList.get(position);
        holder.songTitle.setText(song.getTitle());
        // Set other attributes like image

        Glide.with(holder.itemView.getContext())
                .load(song.getThumbnail())
                .into(holder.songImage);

        // Set the click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("youtubeUrl", song.getYoutubeUrl());
                editor.apply();

                Log.d("SongsAdapter", "YouTube URL set in SharedPreferences: " + song.getYoutubeUrl());

                MainActivity mainActivity = (MainActivity) context;
                mainActivity.loadNavFragment(new HomeFragment());

                mainActivity.bottomNavigationView.setSelectedItemId(R.id.nav_home);
            }
        });

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView songTitle;
        ImageView songImage;

        public ViewHolder(View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.songTitle);
            songImage = itemView.findViewById(R.id.songImage);
        }
    }

}
