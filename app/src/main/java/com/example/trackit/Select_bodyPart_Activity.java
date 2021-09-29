package com.example.trackit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Select_bodyPart_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView textView ;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Workout");
    private Spinner spinner;
    private String bodyPart = "";
    private String text= "";

    private TextView add_workout_textview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bodypart);
        textView =findViewById(R.id.textView5);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        spinner = findViewById(R.id.spinner_add_Workout);
        add_workout_textview = findViewById(R.id.add_workout_textview);
       String userID = "bS6fHhkSfFelWaI6f00fwoWiggC2";
        List<String> body_parts = new ArrayList<>();
        body_parts.add("BACK");
        body_parts.add("CHEST");
        body_parts.add("LEGS");
        body_parts.add("SHOULDER");

        ArrayAdapter<String> myAdapter  = new ArrayAdapter<>(Select_bodyPart_Activity.this ,
                android.R.layout.simple_list_item_1,
                body_parts);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(this);

        add_workout_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Select_bodyPart_Activity.this , Add_routine_popUp_Activity.class);
                intent.putExtra("bodyPart" , bodyPart);
                startActivity(intent);

//                Toast.makeText(Select_bodyPart_Activity.this , "it is clicked" ,Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(Select_bodyPart_Activity.this , Add_rotuine_Activity.class));
            }
        });


//        collectionReference.document("legs").collection("squat")
//                .whereEqualTo("userID" , userID ).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                assert value != null;
//                if(!value.isEmpty()){
//                    for(QueryDocumentSnapshot snapshot : value){
//                        Toast.makeText(Select_bodyPart_Activity.this , "it is clicked" ,Toast.LENGTH_SHORT).show();
//
//                        text += (snapshot.getString("reps"));
//
//                    }
//                }
//                textView.setText(text);
//
//
//            }
//        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         bodyPart = parent.getSelectedItem().toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void check_progress(View view) {

        startActivity(new Intent(Select_bodyPart_Activity.this , List_of_BodyPart_Activity.class));

    }
}