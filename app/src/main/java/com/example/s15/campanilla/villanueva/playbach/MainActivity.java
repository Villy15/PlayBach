package com.example.s15.campanilla.villanueva.playbach;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MainActivity extends AppCompatActivity {

    YouTubePlayerView youTubePlayerView;
    TextView playbackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        playbackText = findViewById(R.id.playbackText);

        initYouTubePlayerView(youTubePlayerView);
    }

//    This function initializes the YouTubePlayerView.
    private void initYouTubePlayerView (YouTubePlayerView youTubePlayerView) {
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "IwInqrN_auU";
                youTubePlayer.loadVideo(videoId, 0);

                setPlaybackSpeedButtonsClickListeners(youTubePlayer);
            }

            @Override
            public void onPlaybackRateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackRate playbackRate) {
                super.onPlaybackRateChange(youTubePlayer, playbackRate);

                String playbackRateString = getPlaybackSpeedString(playbackRate);
                playbackText.setText(String.format("Playback: %s", playbackRateString));
            }

        });
    }

//    This function sets the click listeners for the playback speed buttons.
    private void setPlaybackSpeedButtonsClickListeners (YouTubePlayer youTubePlayer) {
        Button playbackBtn1 = findViewById(R.id.playbackBtn1);
        Button playbackBtn2 = findViewById(R.id.playbackBtn2);
        Button playbackBtn3 = findViewById(R.id.playbackBtn3);
        Button playbackBtn4 = findViewById(R.id.playbackBtn4);


        playbackBtn1.setOnClickListener(view -> youTubePlayer.setPlaybackRate(PlayerConstants.PlaybackRate.RATE_0_25));
        playbackBtn2.setOnClickListener(view -> youTubePlayer.setPlaybackRate(PlayerConstants.PlaybackRate.RATE_0_5));
        playbackBtn3.setOnClickListener(view -> youTubePlayer.setPlaybackRate(PlayerConstants.PlaybackRate.RATE_1));
        playbackBtn4.setOnClickListener(view -> youTubePlayer.setPlaybackRate(PlayerConstants.PlaybackRate.RATE_2));
    }

//    This function returns the playback speed string to a format from RATE_1 to 1x.
    private String getPlaybackSpeedString (PlayerConstants.PlaybackRate playbackRate) {
        String playbackRateString = playbackRate.toString();

        switch (playbackRateString) {
            case "RATE_0_25":
                playbackRateString = "0.25x";
                break;
            case "RATE_0_5":
                playbackRateString = "0.5x";
                break;
            case "RATE_1":
                playbackRateString = "1x";
                break;
            case "RATE_2":
                playbackRateString = "2x";
                break;
        }

        return playbackRateString;
    }
}