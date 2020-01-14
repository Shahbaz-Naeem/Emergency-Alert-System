package com.example.firebasepushnotification;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private Button mLogoutBtn;
    private CircleImageView mProfileImage;
    private TextView mProfileName;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    private String mUserId;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mLogoutBtn = view.findViewById(R.id.profileLogout);
        mProfileImage = view.findViewById(R.id.profileImage);
        mProfileName = view.findViewById(R.id.profile_name);

        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();

        mUserId = mAuth.getUid();

        mFireStore.collection("Users").document(mUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String user_name = documentSnapshot.getString("name");
                String user_image = documentSnapshot.getString("image");

                mProfileName.setText(user_name);

                RequestOptions placeholder = new RequestOptions();
                placeholder.placeholder(R.drawable.default_profile_image);

                Glide.with(container.getContext()).setDefaultRequestOptions(placeholder).load(user_image).into(mProfileImage);
            }
        });


        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Map<String ,Object> tokenRemove = new HashMap<>();
                tokenRemove.put("token_id", FieldValue.delete());

                mFireStore.collection("Users").document(mUserId).update(tokenRemove).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mAuth.signOut();

                        Intent loginIntent = new Intent(container.getContext(),LoginActivity.class);
                        startActivity(loginIntent);
                    }
                });
            }
        });




        return view;
    }

}
