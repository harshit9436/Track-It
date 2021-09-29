package com.example.trackit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.example.trackit.Adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class Exercises_bodyPart_Activity extends AppCompatActivity implements RecyclerViewAdapter.onExerciseClickListener {
    private List<String> exercise_name = new ArrayList<>();
    private String bodyPart;
    private RecyclerView recyclerView;
    private List<String> legs_exercises = new ArrayList<>();
    private List<String> back_exercises = new ArrayList<>();
    private List<String> chest_exercises = new ArrayList<>();
    private List<String> shoulder_exercises = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_body_part);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        bodyPart =  getIntent().getStringExtra("bodyPart").toString().trim();
        recyclerView = findViewById(R.id.recycler_view_exercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        legs_exercises = new ArrayList<>();
        legs_exercises.add("SQUATS");
        legs_exercises.add("SUMO SQUATS");
        legs_exercises.add("LEG PRESS");
        legs_exercises.add("LEG EXTENSION");
        legs_exercises.add("HAMSTRING CURL");
        legs_exercises.add("CALF RAISES");

        back_exercises = new ArrayList<>();
        back_exercises.add("DEAD LIFT");
        back_exercises.add("SEATED ROW PULL");
        back_exercises.add("WINGS PULL DOWN");
        back_exercises.add("PULL UPS");
        back_exercises.add("LAT PULL DOWN");
        back_exercises.add("SINGLE ARM DUMBBELL ROW");


        chest_exercises = new ArrayList<>();
        chest_exercises.add("PUSH UPS");
        chest_exercises.add("BENCH PRESS");
        chest_exercises.add("INCLINED BENCH PRESS");
        chest_exercises.add("DECLINE BENCH PRESS");
        chest_exercises.add("FLYING MACHINE");
        chest_exercises.add("DUMBBELL PRESS");
        chest_exercises.add("INCLINED DUMBBELL PRESS");
        chest_exercises.add("DECLINED DUMBBELL PRESS");
        chest_exercises.add("CABLE FLY");


        shoulder_exercises = new ArrayList<>();
        shoulder_exercises.add("DUMBBELL PRESS");
        shoulder_exercises.add("SIDE RAISES");
        shoulder_exercises.add("FRONT RAISES");
        shoulder_exercises.add("MILITARY PRESS");
        shoulder_exercises.add("BEND DUMBBELL RAISES");

        bodyPart = getIntent().getStringExtra("bodyPart").toString().trim();
        switch (bodyPart){
            case "LEGS":
                recyclerViewAdapter = new RecyclerViewAdapter( legs_exercises , bodyPart , Exercises_bodyPart_Activity.this);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                break;

            case "CHEST":
                recyclerViewAdapter = new RecyclerViewAdapter( chest_exercises , bodyPart,Exercises_bodyPart_Activity.this);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                break;

            case "SHOULDER":
                recyclerViewAdapter = new RecyclerViewAdapter( shoulder_exercises , bodyPart, Exercises_bodyPart_Activity.this);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                break;

            case "BACK":
                recyclerViewAdapter = new RecyclerViewAdapter( back_exercises , bodyPart, Exercises_bodyPart_Activity.this);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_different_bodypart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_new_set_different_bodyPart:
               Intent intent =  new Intent(Exercises_bodyPart_Activity.this, Add_routine_popUp_Activity.class);
                intent.putExtra("bodyPart" , getIntent().getStringExtra("bodyPart").toString().trim());
                startActivity(intent);
                break;


        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onExerciseClick(String exercise_name) {
       Intent intent = new Intent(Exercises_bodyPart_Activity.this , Show_data_Activity.class);
        intent.putExtra("exerciseName" ,exercise_name );
        intent.putExtra("bodyPart" , getIntent().getStringExtra("bodyPart"));
        startActivity(intent);
    }

    @Override
    public void onImageViewClick() {
        startActivity(new Intent(Exercises_bodyPart_Activity.this , List_of_BodyPart_Activity.class));

    }


}