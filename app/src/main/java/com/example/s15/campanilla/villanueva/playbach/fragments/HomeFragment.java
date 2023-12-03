package com.example.s15.campanilla.villanueva.playbach.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.s15.campanilla.villanueva.playbach.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Locale;


public class HomeFragment extends Fragment {

    // YouTube Player
    YouTubePlayerView youTubePlayerView;
    // Current TimeStamp
    float currentTimeStamp;

    // Loop Section
    Switch loopSwitch;
    EditText startTimeStamp, endTimeStamp;
    float startTime = 0, endTime = 10;

    // Playback speeds
    TextView playbackText;

    // Sections
    Button sectionsTxtBtn1, sectionsTxtBtn2, sectionsTxtBtn3, sectionsTxtBtn4, sectionsTxtBtn5, sectionsTxtBtn6, sectionsTxtBtn7, sectionsTxtBtn8;
    float sectionsTimeStamp1, sectionsTimeStamp2, sectionsTimeStamp3, sectionsTimeStamp4, sectionsTimeStamp5, sectionsTimeStamp6, sectionsTimeStamp7, sectionsTimeStamp8;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        youTubePlayerView = view.findViewById(R.id.youtube_player_view);

        // Toggle or hide the layouts using sharedPreferences
        ConstraintLayout loopSection = view.findViewById(R.id.constraintLayout);
        ConstraintLayout playbackSpeedSection = view.findViewById(R.id.constraintLayout2);
        ConstraintLayout sectionsSection = view.findViewById(R.id.constraintLayout3);

        SharedPreferences preferences = getActivity().getSharedPreferences("SettingsPreferences", Context.MODE_PRIVATE);
        loopSection.setVisibility(preferences.getBoolean("toggleSwitch1", true) ? View.VISIBLE : View.GONE);
        playbackSpeedSection.setVisibility(preferences.getBoolean("toggleSwitch2", true) ? View.VISIBLE : View.GONE);
        sectionsSection.setVisibility(preferences.getBoolean("toggleSwitch3", true) ? View.VISIBLE : View.GONE);

        // Loop Section
        loopSwitch = view.findViewById(R.id.loopSwitch);
        startTimeStamp = view.findViewById(R.id.startTimeStamp);
        endTimeStamp = view.findViewById(R.id.endTimeStamp);

        // Playback speeds
        playbackText = view.findViewById(R.id.playbackText);

        initYouTubePlayerView(youTubePlayerView);
    }

    //    This function initializes the YouTubePlayerView.
    private void initYouTubePlayerView (YouTubePlayerView youTubePlayerView) {
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                SharedPreferences preferences = getActivity().getSharedPreferences("SongsAdapterPreferences", Context.MODE_PRIVATE);
                String youtubeUrl = preferences.getString("youtubeUrl", "KLSXITpk854");
                String videoId = youtubeUrl;
                Log.d("HomeFragment", "YouTube URL retrieved from SharedPreferences: " + youtubeUrl);
                youTubePlayer.loadVideo(videoId, 0);

                setLoopSection(youTubePlayer);
                setPlaybackSpeedButtonsClickListeners(youTubePlayer);
                setSections(youTubePlayer);

                loopSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    // print isChecked to console
                    System.out.println("Hi " + isChecked);
                    if (isChecked) {
                        startLooping(youTubePlayer);
                    } else {
                        stopLooping(youTubePlayer);
                    }
                });

                setSectionsClickListeners(youTubePlayer);
            }

            @Override
            public void onPlaybackRateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackRate playbackRate) {
                super.onPlaybackRateChange(youTubePlayer, playbackRate);

                String playbackRateString = getPlaybackSpeedString(playbackRate);
                playbackText.setText(String.format("Playback: %s", playbackRateString));
            }

            @Override
            public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float second) {
                super.onCurrentSecond(youTubePlayer, second);

                currentTimeStamp = second;
            }

        });
    }

    //    This function sets the click listeners for the playback speed buttons.
    private void setPlaybackSpeedButtonsClickListeners (YouTubePlayer youTubePlayer) {
        Button playbackBtn1 = getView().findViewById(R.id.playbackBtn1);
        Button playbackBtn2 = getView().findViewById(R.id.playbackBtn2);
        Button playbackBtn3 = getView().findViewById(R.id.playbackBtn3);
        Button playbackBtn4 = getView().findViewById(R.id.playbackBtn4);


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

    // This function sets the loop section.
    private void setLoopSection(YouTubePlayer youTubePlayer) {
        Button startBtn = getView().findViewById(R.id.startBtn);
        Button endBtn = getView().findViewById(R.id.endBtn);

        startBtn.setOnClickListener(view -> {
            startTime = currentTimeStamp;
            startTimeStamp.setText(formatTime(startTime));
        });

        endBtn.setOnClickListener(view -> {
            endTime = currentTimeStamp;
            endTimeStamp.setText(formatTime(startTime));
        });

    }
    // This function formats the time from seconds to minutes and seconds.
    private String formatTime(float timeInSeconds) {
        int totalSeconds = (int) timeInSeconds;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    private AbstractYouTubePlayerListener loopingListener;

    //   This function starts the looping.
    private void startLooping(YouTubePlayer youTubePlayer) {
        loopingListener = new AbstractYouTubePlayerListener() {
            @Override
            public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float second) {
                super.onCurrentSecond(youTubePlayer, second);

                if (second >= endTime) {
                    youTubePlayer.seekTo(startTime);
                }
            }
        };

        youTubePlayer.addListener(loopingListener);
        youTubePlayer.seekTo(startTime);
        youTubePlayer.play();
    }

    private void stopLooping(YouTubePlayer youTubePlayer) {
        if (loopingListener != null) {
            youTubePlayer.removeListener(loopingListener);
            loopingListener = null;
        }
    }

    private void setSections (YouTubePlayer youTubePlayer) {
        Button sectionsBtn1 = getView().findViewById(R.id.sectionsBtn1);
        Button sectionsBtn2 = getView().findViewById(R.id.sectionsBtn2);
        Button sectionsBtn3 = getView().findViewById(R.id.sectionsBtn3);
        Button sectionsBtn4 = getView().findViewById(R.id.sectionsBtn4);
        Button sectionsBtn5 = getView().findViewById(R.id.sectionsBtn5);
        Button sectionsBtn6 = getView().findViewById(R.id.sectionsBtn6);
        Button sectionsBtn7 = getView().findViewById(R.id.sectionsBtn7);
        Button sectionsBtn8 = getView().findViewById(R.id.sectionsBtn8);

        sectionsBtn1.setOnClickListener(view -> {
            sectionsTimeStamp1 = currentTimeStamp;
            sectionsTxtBtn1.setText(formatTime(currentTimeStamp));
        });

        sectionsBtn2.setOnClickListener(view -> {
            sectionsTimeStamp2 = currentTimeStamp;
            sectionsTxtBtn2.setText(formatTime(currentTimeStamp));
        });

        sectionsBtn3.setOnClickListener(view -> {
            sectionsTimeStamp3 = currentTimeStamp;
            sectionsTxtBtn3.setText(formatTime(currentTimeStamp));
        });

        sectionsBtn4.setOnClickListener(view -> {
            sectionsTimeStamp4 = currentTimeStamp;
            sectionsTxtBtn4.setText(formatTime(currentTimeStamp));
        });

        sectionsBtn5.setOnClickListener(view -> {
            sectionsTimeStamp5 = currentTimeStamp;
            sectionsTxtBtn5.setText(formatTime(currentTimeStamp));
        });

        sectionsBtn6.setOnClickListener(view -> {
            sectionsTimeStamp6 = currentTimeStamp;
            sectionsTxtBtn6.setText(formatTime(currentTimeStamp));
        });

        sectionsBtn7.setOnClickListener(view -> {
            sectionsTimeStamp7 = currentTimeStamp;
            sectionsTxtBtn7.setText(formatTime(currentTimeStamp));
        });

        sectionsBtn8.setOnClickListener(view -> {
            sectionsTimeStamp8 = currentTimeStamp;
            sectionsTxtBtn8.setText(formatTime(currentTimeStamp));
        });
    }

    private void setSectionsClickListeners (YouTubePlayer youTubePlayer) {
        sectionsTxtBtn1 = getView().findViewById(R.id.sectionsTxtBtn1);
        sectionsTxtBtn2 = getView().findViewById(R.id.sectionsTxtBtn2);
        sectionsTxtBtn3 = getView().findViewById(R.id.sectionsTxtBtn3);
        sectionsTxtBtn4 = getView().findViewById(R.id.sectionsTxtBtn4);
        sectionsTxtBtn5 = getView().findViewById(R.id.sectionsTxtBtn5);
        sectionsTxtBtn6 = getView().findViewById(R.id.sectionsTxtBtn6);
        sectionsTxtBtn7 = getView().findViewById(R.id.sectionsTxtBtn7);
        sectionsTxtBtn8 = getView().findViewById(R.id.sectionsTxtBtn8);

        sectionsTxtBtn1.setOnClickListener(view -> jumpToSection(youTubePlayer, sectionsTimeStamp1));
        sectionsTxtBtn2.setOnClickListener(view -> jumpToSection(youTubePlayer, sectionsTimeStamp2));
        sectionsTxtBtn3.setOnClickListener(view -> jumpToSection(youTubePlayer, sectionsTimeStamp3));
        sectionsTxtBtn4.setOnClickListener(view -> jumpToSection(youTubePlayer, sectionsTimeStamp4));
        sectionsTxtBtn5.setOnClickListener(view -> jumpToSection(youTubePlayer, sectionsTimeStamp5));
        sectionsTxtBtn6.setOnClickListener(view -> jumpToSection(youTubePlayer, sectionsTimeStamp6));
        sectionsTxtBtn7.setOnClickListener(view -> jumpToSection(youTubePlayer, sectionsTimeStamp7));
        sectionsTxtBtn8.setOnClickListener(view -> jumpToSection(youTubePlayer, sectionsTimeStamp8));

    }

    private void jumpToSection (YouTubePlayer youTubePlayer, float timeStamp) {
        youTubePlayer.seekTo(timeStamp);
    }

}