package com.example.firebasepushnotification;

import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class SendAlert {

    private final String mUserId;
    private FirebaseFirestore mFireStore;

    public SendAlert() {

        mFireStore = FirebaseFirestore.getInstance();
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mFireStore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                HashMap<String,Object> notification = new HashMap<>();
                notification.put("message", "location");
                notification.put("from",mUserId);


                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                    String mNotificationTo = doc.getDocument().getId();
                    if(!mNotificationTo.equals(mUserId))
                        mFireStore.collection("Users/"+mNotificationTo+"/Notifications").add(notification);
                }
            }
        });

    }
}
