package com.example.trackit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");
    private TextView login_textview , signUp_textview;
    private EditText email_ID_et , password_et;
    private ProgressBar progressBar_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        login_textview = findViewById(R.id.login_acc_textview);
        signUp_textview = findViewById(R.id.sign_up_textview);
        email_ID_et =findViewById(R.id.email_login_et);
        password_et = findViewById(R.id.password_login_et);
        progressBar_login = findViewById(R.id.progressBar_login);

        signUp_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , RegisterActivity.class));
                finish();
            }
        });

        login_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_ID = email_ID_et.getText().toString().trim();
                String password = password_et.getText().toString().trim();
                if(!TextUtils.isEmpty(email_ID) && ! TextUtils.isEmpty(password)){
                    signInUser(email_ID , password);
                }else{
                    Toast.makeText(LoginActivity.this , "Enter all fields" , Toast.LENGTH_SHORT).show();
                }
            }
        });


        authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            currentUser = firebaseAuth.getCurrentUser();
            if(currentUser!=null){

                String userID = currentUser.getUid();
                collectionReference.whereEqualTo("userID" , userID)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        assert value != null;
                        if(!value.isEmpty()){
                            for(QueryDocumentSnapshot snapshot : value){
                                UserApi userApi = UserApi.getInstance();
                                userApi.setUserID(userID);
                                userApi.setUsername(snapshot.getString("username"));
                                startActivity(new Intent(LoginActivity.this , Select_bodyPart_Activity.class));
                                finish();
                            }
                        }
                    }
                });
            }else{

            }
        }
    };





    }

    private void signInUser(String email_id, String password) {
        if(!TextUtils.isEmpty(email_id) && ! TextUtils.isEmpty(password)){
            progressBar_login.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(email_id, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        currentUser = firebaseAuth.getCurrentUser();
                        assert currentUser != null;
                        String userID = currentUser.getUid();
                        collectionReference.whereEqualTo("userID" , userID).addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                assert value != null;
                                if(!value.isEmpty()){
                                    for(QueryDocumentSnapshot snapshot : value){
                                        UserApi userApi = UserApi.getInstance();
                                        userApi.setUserID(userID);
                                        userApi.setUsername(snapshot.getString("username"));
                                        startActivity(new Intent(LoginActivity.this , Select_bodyPart_Activity.class));
                                        finish();

                                    }
                                }else{
                                    assert error != null;
                                    Log.d("on Snapshot", "onEvent: "+ error.toString());
                                }
                            }
                        });

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("trying login", "onFailure: "+ e.toString());
                    progressBar_login.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this , "Wrong Email or Password" , Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(LoginActivity.this , "Enter all fields" , Toast.LENGTH_SHORT).show();
        }

        }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);


    }


    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}