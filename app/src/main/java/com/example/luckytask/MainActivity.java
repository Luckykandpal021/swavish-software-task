package com.example.luckytask;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);

        // Check if user is already logged in
        if (sharedPreferences.getBoolean("isUserLoggedIn", false)) {
            // User is already logged in, navigate to the main activity
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentRegistrationContainer, HomeFragment.getInstance()).commit();

        } else {
            // User is not logged in, show the login screen
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentRegistrationContainer, RegisterScreen.getInstance()).commit();
        }


    }
}