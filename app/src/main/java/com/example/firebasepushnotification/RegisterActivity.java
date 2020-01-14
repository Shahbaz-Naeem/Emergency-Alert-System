package com.example.firebasepushnotification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private EditText mEmailField;
    private EditText mNameField;
    private EditText mPasswordField;
    private Button mRegisterButton;
    private Button mLoginButton;
    private ProgressBar mRegisterProgressBar;
    private CircleImageView mImageBtn;

    private Uri imageUri;

    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailField = findViewById(R.id.register_email);
        mNameField = findViewById(R.id.register_name);
        mPasswordField = findViewById(R.id.register_password);
        mRegisterButton = findViewById(R.id.register_btn);
        mLoginButton = findViewById(R.id.register_login_btn);
        mRegisterProgressBar = findViewById(R.id.register_progress_bar);
        mImageBtn = findViewById(R.id.register_image_btn);

        imageUri = null;

        mStorage = FirebaseStorage.getInstance().getReference("images");
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 if(imageUri != null)
                 {
                     final String name = mNameField.getText().toString();
                     final String email = mEmailField.getText().toString();
                     final String password = mPasswordField.getText().toString();

                     if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                     {
                         mRegisterProgressBar.setVisibility(View.VISIBLE);
                         mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if(task.isSuccessful()){
                                     // Send the user to an another activity which selects images, name and other task for final App
                                     final String  user_id = mAuth.getCurrentUser().getUid();

                                     final StorageReference user_profile = mStorage.child(user_id+".jpg");
                                     user_profile.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                         @Override
                                         public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot)
                                         {

                                             mStorage.child(user_id+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                 @Override
                                                 public void onSuccess(Uri uri) {
                                                     final String download_uri = uri.toString();

                                                     String token_id = FirebaseInstanceId.getInstance().getToken();

                                                     Map<String,Object> userMap = new HashMap<>();
                                                     userMap.put("image",download_uri);
                                                     userMap.put("name",name);
                                                     userMap.put("token_id",token_id);

                                                     mFireStore.collection("Users").document(user_id).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>()
                                                     {
                                                         @Override
                                                         public void onSuccess(Void mVoid)
                                                         {
                                                             mRegisterProgressBar.setVisibility(View.INVISIBLE);
                                                             sendToMain();
                                                         }
                                                     });
                                                 }
                                             });
                                         }
                                     });
                                 }
                                 else {
                                     mRegisterProgressBar.setVisibility(View.INVISIBLE);
                                     Toast.makeText(getApplicationContext(),"Error :" + task.getException(), Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });
                     }
                 }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE)
        {
             imageUri = data.getData();
             mImageBtn.setImageURI(imageUri) ;

        }

    }

    private void sendToMain() {
        Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
