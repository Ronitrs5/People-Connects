package com.example.peopleconnects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.peopleconnects.fragment.HomeFragment;
import com.example.peopleconnects.fragment.MessageFragment;
import com.example.peopleconnects.fragment.NotificationFragment;
import com.example.peopleconnects.fragment.PostFragment;
import com.example.peopleconnects.fragment.ProfileFragment;
import com.example.peopleconnects.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        bottomNavigationView= findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment= new HomeFragment();
                            break;

                        case R.id.nav_createpost:
                            selectedFragment= null;
                            startActivity(new Intent(MainActivity.this, CreatePostActivity.class));
                            break;

                        case R.id.nav_notification:
                            selectedFragment= new NotificationFragment();
                            break;


                        case R.id.nav_search:
                            selectedFragment= new SearchFragment();
                            break;

                        case R.id.nav_profile:
                            SharedPreferences.Editor editor= getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    if (selectedFragment!=null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    }

                    return true;
                }
            };
}