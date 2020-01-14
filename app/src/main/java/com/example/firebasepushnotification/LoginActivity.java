package com.example.firebasepushnotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginBtn;
    private Button mRegisterButton;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = findViewById(R.id.login_email);
        mPasswordField = findViewById(R.id.login_password);
        mLoginBtn = findViewById(R.id.login_btn);
        mRegisterButton = findViewById(R.id.login_register_btn);
        mProgressBar = findViewById(R.id.loginProgressBar);

        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(regIntent);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailField.getText().toString();
                String password = mPasswordField.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                String current_id = mAuth.getCurrentUser().getUid();
                                String token_id = FirebaseInstanceId.getInstance().getToken();

                                Map<String,Object> tokenMap = new HashMap<>();
                                tokenMap.put("token_id",token_id);

                                mFireStore.collection("Users").document(current_id).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mProgressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"Toast" , Toast.LENGTH_SHORT).show();
                                        sendToMain();
                                    }
                                });
                            }
                            else
                            {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(),"Error : " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });



    }

    private void sendToMain() {
        Intent main = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(main);
        finish();
    }
}
