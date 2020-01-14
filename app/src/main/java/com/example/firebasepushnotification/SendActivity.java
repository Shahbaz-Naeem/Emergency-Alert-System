package com.example.firebasepushnotification;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SendActivity extends AppCompatActivity {

    private TextView user_id_view;
    private EditText mMessageView;
    private Button mSendBtn;
    private ProgressBar mProgressBar;

    private String mUserId;
    private String mUserNaame;
    private String mCurrentId;

    private FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        user_id_view = findViewById(R.id.user_name_view);
        mMessageView = findViewById(R.id.message_view);
        mSendBtn = findViewById(R.id.send_btn);
        mProgressBar = findViewById(R.id.notification_progressBar);

        mUserId = getIntent().getStringExtra("user_id");
        mUserNaame = getIntent().getStringExtra("user_name");
        mCurrentId = FirebaseAuth.getInstance().getUid();

        user_id_view.setText("Send to " + mUserNaame);

        mFireStore = FirebaseFirestore.getInstance();

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] message = {mMessageView.getText().toString()};

                mProgressBar.setVisibility(View.VISIBLE);


                if(!TextUtils.isEmpty(message[0]))
                {
                    HashMap<String,Object> notification = new HashMap<>();
                    notification.put("message", message[0]);
                    notification.put("from",mCurrentId);

                    mFireStore.collection("Users/"+mUserId+"/Notifications").add(notification).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(SendActivity.this,"Notification Sent",Toast.LENGTH_SHORT).show();
                            mMessageView.setText("");
                            mProgressBar.setVisibility(View.INVISIBLE);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SendActivity.this,"Error : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }


            }
        });

    }
}
