package com.example.peopleconnects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Objects.requireNonNull(getSupportActionBar()).hide();

        firebaseAuth= FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseAuth.getCurrentUser()==null){
                    Intent intent= new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }

                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

            }
        }, 1000);
    }
}