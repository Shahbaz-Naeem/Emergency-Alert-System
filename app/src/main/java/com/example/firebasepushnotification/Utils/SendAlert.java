package com.example.firebasepushnotification.Utils;

import android.content.Context;
import android.location.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;

public class SendAlert {

    private final String mUserId;
    private FirebaseFirestore mFireStore;

    public SendAlert(Context context) {

        final FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        mFireStore = FirebaseFirestore.getInstance();
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mFireStore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                final String[] locationData = new String[2];
                locationData[0] = "31.474858";
                locationData[1] = "74.357972";
                String message = "Help";

                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        locationData[0] = Double.toString(location.getLatitude());
                        locationData[1] = Double.toString(location.getLongitude());
                    }
                });


                HashMap<String,Object> notification = new HashMap<>();
                notification.put("message", message);
                notification.put("from",mUserId);
                notification.put("longitude",locationData[1]);
                notification.put("latitude",locationData[0]);
                notification.put("timestamp", Timestamp.now());

                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                    String mNotificationTo = doc.getDocument().getId();

                    //if(!mNotificationTo.equals(mUserId))
                        mFireStore.collection("Users/"+mNotificationTo+"/Notifications").add(notification);

                }
            }
        });

    }
}