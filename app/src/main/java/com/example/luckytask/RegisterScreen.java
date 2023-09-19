package com.example.luckytask;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class RegisterScreen extends Fragment {
    EditText emailEditText, passwordEditText;
    Button signUpBtn;

    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    public static RegisterScreen getInstance() {
        return new RegisterScreen();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register_screen, container, false);


        emailEditText = rootView.findViewById(R.id.emailIdEditText);
        passwordEditText = rootView.findViewById(R.id.passwordEditText);
        signUpBtn = rootView.findViewById(R.id.signUpBtn);
        TextView signInBtn = rootView.findViewById(R.id.signInBtn);
        TextView headingText = rootView.findViewById(R.id.headingText);

        signUpBtn.setOnClickListener(v -> {
            mAuth = FirebaseAuth.getInstance();
            ProgressBar loadingProgressBar = rootView.findViewById(R.id.loadingProgressBar);
            loadingProgressBar.setVisibility(View.VISIBLE);

            // Store a reference to MainActivity

            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString()).addOnCompleteListener(getActivity(), authResult -> {
                loadingProgressBar.setVisibility(View.GONE);

                if (authResult.isSuccessful()) {
                    Toast.makeText(getContext(), "Register Successfull", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fragmentRegistrationContainer, UploadProfilePhoto.getInstance());

                    fragmentTransaction.commit();

                    // Navigate to the main activity


                } else {
                    Toast.makeText(getContext(), "Email Id Already Exist", Toast.LENGTH_SHORT).show();
                }
            });
        });


        signInBtn.setOnClickListener(v -> {
            // Replace the current fragment with DisplayImageFragment and pass the URI
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragmentRegistrationContainer, LoginFragment.getInstance());

            fragmentTransaction.commit();
        });

        return rootView;
    }
}