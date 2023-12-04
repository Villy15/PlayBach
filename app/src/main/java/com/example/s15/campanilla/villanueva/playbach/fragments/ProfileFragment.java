package com.example.s15.campanilla.villanueva.playbach.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.s15.campanilla.villanueva.playbach.LoginActivity;
import com.example.s15.campanilla.villanueva.playbach.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    Button logoutButton;

    TextView textViewDisplayName;

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


    }
}