package com.example.luckytask;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.UUID;

public class UploadProfilePhoto extends Fragment {

    Button uploadBtn, nextHomeBtn;
    ImageView uploadedImage;
    SharedPreferences sharedPreferences;
    Uri uplaodedUri;

    private ActivityResultLauncher<String> imagePickerLauncher;

    public static UploadProfilePhoto getInstance() {
        // Required empty public constructor
        return new UploadProfilePhoto();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_upload_profile_photo, container, false);
        uploadBtn = view.findViewById(R.id.uploadBtn);
        uploadedImage = view.findViewById(R.id.uploadedImage);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        // Get the shared preferences from the activity
        sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        nextHomeBtn = view.findViewById(R.id.nextHomeBtn);
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                uplaodedUri = uri;

                uploadedImage.setImageURI(uri);
            } else {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

//        uploadBtn.setOnClickListener(v -> {
//
//            imagePickerLauncher.launch("image/*");
//            nextHomeBtn.setVisibility(View.VISIBLE);
//
//        });

        uploadBtn.setOnClickListener(v -> {
            imagePickerLauncher.launch("image/*");
            nextHomeBtn.setVisibility(View.VISIBLE);
        });

        nextHomeBtn.setOnClickListener(v -> {
            try {

//                if (uplaodedUri != null) {
//                    // Create a reference to 'images/mountains.jpg'
//                    StorageReference reference = storageReference.child("image/" + UUID.randomUUID().toString());
//                    reference.putFile(uplaodedUri).addOnSuccessListener(taskSnapshot -> Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> {
//                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
//                    });
//
//                }
                sharedPreferences.edit().putBoolean("isUserLoggedIn", true).apply();

                HomeFragment homeFragment=new HomeFragment();
                Bundle bundle=new Bundle();
                bundle.putString("uploadedURI",uplaodedUri.toString());
                homeFragment.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentRegistrationContainer, homeFragment);

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
