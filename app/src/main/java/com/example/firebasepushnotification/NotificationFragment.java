package com.example.firebasepushnotification;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private List<Notification> mNotificationList;
    private RecyclerView recyclerView;
    private NotificationRecyclerAdapter notificationRecyclerAdapter;

    private FirebaseFirestore mFireStore;

    private String current_user_id;

    public NotificationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = view.findViewById(R.id.notification_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        mNotificationList = new ArrayList<>();

        notificationRecyclerAdapter = new NotificationRecyclerAdapter(mNotificationList,getContext());

        mFireStore = FirebaseFirestore.getInstance();

        recyclerView.setAdapter(notificationRecyclerAdapter);

        current_user_id = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mNotificationList.clear();

        mFireStore.collection("Users").document(current_user_id).collection("Notifications").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                    Notification notification = doc.getDocument().toObject(Notification.class);
                    mNotificationList.add(notification);

                    notificationRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });

    }

}
