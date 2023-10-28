package com.example.s15.campanilla.villanueva.playbach;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.s15.campanilla.villanueva.playbach.fragments.HomeFragment;
import com.example.s15.campanilla.villanueva.playbach.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        funBottomNavigationView();
    }

//    Gathered from https://gist.github.com/codinginflow/8a728a27a78e92876ca1c71b3dce28f6
    private void funBottomNavigationView() {
        // Set Default item: when app open
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        // Set Default fragment
        loadNavFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    loadNavFragment(new HomeFragment());
                } else if (id == R.id.nav_profile) {
                    loadNavFragment(new ProfileFragment());
                }

                return true;
            }
        });
    }

    private void loadNavFragment(Fragment fragment) {
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

        //use replace
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }
}