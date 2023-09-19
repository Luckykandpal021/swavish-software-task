package com.example.luckytask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private EditText emailEditText, passwordEditText;
    private Button loginBtn;
    private TextView createAccount, accountNotFound;
    private FirebaseAuth mAuth;

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initializeViews(view);
        setOnClickListeners();
        return view;
    }

    private void initializeViews(View view) {
        emailEditText = view.findViewById(R.id.loginEmail);
        passwordEditText = view.findViewById(R.id.loginPassword);
        loginBtn = view.findViewById(R.id.loginBtn);
        createAccount = view.findViewById(R.id.createBtn);
        accountNotFound = view.findViewById(R.id.accountNotFound);
    }

    private void setOnClickListeners() {
        mAuth = FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(v -> {

            ProgressBar loadingProgressBar = requireView().findViewById(R.id.loginLoadingProgressBar);
            loadingProgressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString()).addOnCompleteListener(requireActivity(), authResult -> {
                loadingProgressBar.setVisibility(View.GONE);

                if (authResult.isSuccessful()) {
//                    accountNotFound.setVisibility(View.VISIBLE);
//                    accountNotFound.setText("ACCOUNT FOUND");
                    Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fragmentRegistrationContainer, UploadProfilePhoto.getInstance());

                    fragmentTransaction.commit();
                } else {

                    Toast.makeText(requireContext(), "Account not found", Toast.LENGTH_SHORT).show();
                    accountNotFound.setVisibility(View.VISIBLE);
                }
            });
        });

        createAccount.setOnClickListener(v -> {
            // Replace the current fragment with RegisterScreen
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentRegistrationContainer, RegisterScreen.getInstance());
            fragmentTransaction.commit();
        });
    }
}
