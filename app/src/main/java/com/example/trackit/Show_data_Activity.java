package com.example.trackit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackit.Adapter.showDataAdapter;
import com.example.trackit.Util.Exercise_Set;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Show_data_Activity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private CollectionReference collectionReference = db.collection("Workout");
    List<Exercise_Set> exercise_setList = new ArrayList<>();
    String exercise_name ;
    String bodyPart;
    String currentUserID;
    private RecyclerView recyclerView;
    private showDataAdapter showDataAdapter;
    private TextView textview;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
         textview = findViewById(R.id.textView10);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView = findViewById(R.id.recyclerView_showData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        floatingActionButton = findViewById(R.id.FAB_showCharts);
        currentUserID = currentUser.getUid();
        exercise_name = getIntent().getStringExtra("exerciseName");
        bodyPart = getIntent().getStringExtra("bodyPart");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Show_data_Activity.this , BarChart_Activity.class);
                intent.putExtra("exerciseName" , getIntent().getStringExtra("exerciseName"));
                startActivity(intent);
            }
        });
       // Toast.makeText(Show_data_Activity.this , "SIZE:"  + exercise_setList.size() ,Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        String currentUserID = currentUser.getUid();
        String exercise_name = getIntent().getStringExtra("exerciseName");
        String bodyPart = getIntent().getStringExtra("bodyPart");
//        db.document("CHEST").collection("PUSH UPS").whereEqualTo("userID" , currentUserID )
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        assert value != null;
//                        if(!value.isEmpty()){
//                            for (QueryDocumentSnapshot snapshot : value){
//                                reps =  snapshot.getString("reps");
//                                Toast.makeText(Show_data_Activity.this , "DATA SET AVAILABLE" , Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                    }
//                });


        db.collection(exercise_name).whereEqualTo("userID" , currentUserID).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                                Exercise_Set exerciseSet = snapshot.toObject(Exercise_Set.class);
                                exercise_setList.add(exerciseSet);
                            }
                            showDataAdapter = new showDataAdapter(Show_data_Activity.this , exercise_setList);
                            recyclerView.setAdapter(showDataAdapter);
                            showDataAdapter.notifyDataSetChanged();

                        }
                        if(exercise_setList.size()==0){
                            textview.setVisibility(View.VISIBLE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("showData_adapter", "onFailure: "+ e.toString());
            }
        });

        exercise_setList.removeAll(exercise_setList);


    }
}