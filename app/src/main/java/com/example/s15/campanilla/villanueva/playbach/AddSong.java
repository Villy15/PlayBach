package com.example.s15.campanilla.villanueva.playbach;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.s15.campanilla.villanueva.playbach.Classes.Songs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddSong extends AppCompatActivity {

    private EditText editTextYoutubeLink;
    private EditText editTextSongTitle;
    private Button submitButton;

    private TextView temporaryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_songs);

        // Initialize EditText fields and submit button
        editTextYoutubeLink = findViewById(R.id.editTextYoutubeLink);
        editTextSongTitle = findViewById(R.id.editTextSongTitle);
        submitButton = findViewById(R.id.submitButton);
        temporaryTextView = findViewById(R.id.temporaryTextView);


        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youtubeLink = editTextYoutubeLink.getText().toString();

                String videoId = extractVideoId(youtubeLink);

                String songTitle = editTextSongTitle.getText().toString();

                String imageUrl = "https://img.youtube.com/vi/" + videoId + "/0.jpg";

                // Get the name of the contributor from the Firebase user
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String contributorName = currentUser.getDisplayName();

                DBManager dbManager = new DBManager();

                dbManager.addSong(songTitle, videoId, imageUrl, contributorName);

                // Go back to Songs Fragment
                finish();
            }
        });
    }

    private String extractVideoId(String youtubeLink) {
        String videoId = null;

        Pattern pattern = Pattern.compile("(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed'\\/|watch\\?v=|\\?v=|\\&v=|youtu.be\\/|\\/v\\/|watch\\?v=|\\?v=)([a-zA-Z0-9_-]{11})");

        Matcher matcher = pattern.matcher(youtubeLink);

        if (matcher.find()) {
            videoId = matcher.group(1);
        }

        return videoId;
    }
}