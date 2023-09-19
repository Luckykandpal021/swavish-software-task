package com.example.luckytask;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {

    Button signOutBtn;
    SharedPreferences sharedPreferences;

    ImageView homeImage;
    TextView currentUser;

    public static HomeFragment getInstance() {

        return new HomeFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Get the shared preferences from the activity
        if(isAdded()) {
            sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        }


        currentUser=view.findViewById(R.id.username);
        homeImage = view.findViewById(R.id.homeImage);
        signOutBtn = view.findViewById(R.id.signOutBtn);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            currentUser.setText(email);
            // Use the email
        } else {
            currentUser.setText("Something Went Wrong");
            // No user is signed in
        }

        try {
            Bundle args = getArguments();
            if (args != null) {
                String imageUri = args.getString("uploadedURI"); // Change "imageUri" to "UploadUri"
                if (imageUri != null) {
                    homeImage.setImageURI(Uri.parse(imageUri));
                }
            }
        } catch (Exception e) {
            Log.e("ARGS ERROR", e.toString());
        }


        signOutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            try {
                sharedPreferences.edit().putBoolean("isUserLoggedIn", false).apply();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragmentRegistrationContainer, RegisterScreen.getInstance());

                fragmentTransaction.commit();

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("NextBtnException", e.toString());
                // Add additional error handling, such as showing a toast or logging the exception.
            }

        });


        return view;
    }
}