package com.example.trackit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackit.Util.UserApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText username_et , email_id_et , password_et , confirmPass_et;
    private TextView singIN_textview , register_textview;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        username_et = findViewById(R.id.username_et_register);
        email_id_et = findViewById(R.id.email_ID_et_register);
        password_et = findViewById(R.id.pass_et_register);
        confirmPass_et = findViewById(R.id.confirm_pass_register);
        singIN_textview = findViewById(R.id.sign_in_textview);
        register_textview = findViewById(R.id.register_textview);
        progressBar = findViewById(R.id.progressBar_register);
        firebaseAuth = FirebaseAuth.getInstance();

        register_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(username_et.getText())
                        && !TextUtils.isEmpty(email_id_et.getText())
                        && !TextUtils.isEmpty(password_et.getText())
                        && !TextUtils.isEmpty(confirmPass_et.getText())){
                    if(password_et.getText().toString().trim().equals(confirmPass_et.getText().toString().trim())){
                        String email_id = email_id_et.getText().toString().trim();
                        String password = password_et.getText().toString().trim();
                        progressBar.setVisibility(View.VISIBLE);
                        createUserAccount(email_id , password );




                    }else{
                        Toast.makeText(RegisterActivity.this, "Passwords do not match" , Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this, "Enter All fields" , Toast.LENGTH_SHORT).show();
                }
            }
        });


        singIN_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void createUserAccount(String email_id, String password) {

        if(!TextUtils.isEmpty(email_id) && !TextUtils.isEmpty(password)){
            firebaseAuth.createUserWithEmailAndPassword(email_id, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        currentUser = firebaseAuth.getCurrentUser();
                        String username = username_et.getText().toString().trim();
                        String userID = currentUser.getUid();
                        String password  = password_et.getText().toString().trim();
                        Map<String , String> userObj = new HashMap<>();
                        userObj.put("username" , username);
                        userObj.put("userID" , userID);
                        userObj.put("password", password);
                        collectionReference.add(userObj).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()){

                                    Intent intent = new Intent(RegisterActivity.this , Select_bodyPart_Activity.class);
                                    intent.putExtra("username" , username);
                                    intent.putExtra("userID" , userID);
                                    UserApi userApi = UserApi.getInstance();
                                    userApi.setUsername(username);
                                    userApi.setUserID(userID);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.GONE);
                                Log.d("creating account", "onFailure: "+ e.toString());
                            }
                        });
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Log.d("creating account", "onFailure: "+ e.toString());
                }
            });
        }



    }
}