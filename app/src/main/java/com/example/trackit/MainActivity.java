package com.example.trackit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.trackit.Util.UserApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    private TextView getStarted_textview;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private CollectionReference  collectionReference = db.collection("Users");
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getStarted_textview = findViewById(R.id.getStarted_textview);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar_mainActivity);

        getStarted_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                finish();
                currentUser = firebaseAuth.getCurrentUser();
                if(currentUser!=null){
                    //AutologinUser();

                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    progressBar.setVisibility(View.VISIBLE);

                }else {

                    Log.d("firebase", "onEvent: " + "current user?null");

                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
   //    AutologinUser();

    }

//    private void AutologinUser() {
//
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                currentUser = firebaseAuth.getCurrentUser();
//                if(currentUser!=null){
//
//                    String userID = currentUser.getUid();
//                    collectionReference.whereEqualTo("userID" , userID)
//                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                @Override
//                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                                    assert value != null;
//                                    if(!value.isEmpty()){
//                                        for(QueryDocumentSnapshot snapshot : value){
//                                            UserApi userApi = UserApi.getInstance();
//                                            Log.d("firebase", "onEvent: " + "current user!=null");
//                                            userApi.setUserID(userID);
//                                            userApi.setUsername(snapshot.getString("username"));
//                                            startActivity(new Intent(MainActivity.this , Select_bodyPart_Activity.class));
//                                            finish();
//                                        }
//                                    }
//                                }
//                            });
//                }else{
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                    finish();
//
//                    Log.d("firebase", "onEvent: " + "current user==null");
//
//
//                }
//            }
//        };


   // }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        currentUser = firebaseAuth.getCurrentUser();
//        firebaseAuth.addAuthStateListener(authStateListener);
//
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        firebaseAuth.removeAuthStateListener(authStateListener);
//    }
}