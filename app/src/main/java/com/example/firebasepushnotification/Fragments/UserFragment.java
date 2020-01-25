package com.example.firebasepushnotification.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasepushnotification.R;
import com.example.firebasepushnotification.Models.User;
import com.example.firebasepushnotification.Adapters.UserRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    RecyclerView mRecyclerView;
    UserRecyclerAdapter mUserRecyclerAdapter;

    List<User> usersList;
    private FirebaseFirestore mFireStore;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        mFireStore = FirebaseFirestore.getInstance();

        mRecyclerView = view.findViewById(R.id.user_recyclerView);
        usersList = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mUserRecyclerAdapter = new UserRecyclerAdapter(container.getContext(),usersList);
        mRecyclerView.setAdapter(mUserRecyclerAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        usersList.clear();

        mFireStore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED)
                    {
                        String user_id = doc.getDocument().getId();

                        User user = doc.getDocument().toObject(User.class).withId(user_id);
                        usersList.add(user);

                        mUserRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
