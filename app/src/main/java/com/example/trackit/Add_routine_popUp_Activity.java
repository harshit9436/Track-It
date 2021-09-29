package com.example.trackit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackit.Util.Exercise_Set;
import com.example.trackit.Util.UserApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Add_routine_popUp_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Spinner exercise_spinner;
//    private CollectionReference  collectionReference = db.collection("Workout");
    private EditText reps_et , weight_et;
    private List<String> list_final = new ArrayList<>();
    private List<String> legs_exercises = new ArrayList<>();
    private List<String> back_exercises = new ArrayList<>();
    private List<String> chest_exercises = new ArrayList<>();
    private List<String> shoulder_exercises = new ArrayList<>();
    private ArrayAdapter<String> myAdapter;
    private Button save_set_button , save_exercise_button;
    private TextView set_number_textview;
    private String exercise_name , bodyPart;
    private ProgressBar save_set_progressbar;
    private int counter = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine_popup);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
      //  Objects.requireNonNull(AppCompatActivity.getSupportActionBar()).hide();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        exercise_spinner = findViewById(R.id.spinner_exercises_list);
        reps_et = findViewById(R.id.reps_editTextView);
        weight_et = findViewById(R.id.weight_EditTextView);
        save_set_button = findViewById(R.id.save_set_button);
        save_set_progressbar = findViewById(R.id.save_set_progressBar);
        set_number_textview = findViewById(R.id.set_number_Textview);
        save_exercise_button = findViewById(R.id.save_complete_exercise_button);
        Dialog dialog = new Dialog(Add_routine_popUp_Activity.this);
        dialog.setContentView(R.layout.dailog_save_exercise);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.WRAP_CONTENT  );
        dialog.setCancelable(true);

        TextView allDone_textview = dialog.findViewById(R.id.allDone_textview);
        TextView add_exercise_textview = dialog.findViewById(R.id.add_another_exercise_textview);

        allDone_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Add_routine_popUp_Activity.this , List_of_BodyPart_Activity.class));
                dialog.dismiss();

            }
        });
        add_exercise_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                counter=1;
                set_number_textview.setText("SET 1");

            }

        });


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

//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//        int width = (int) (metrics.widthPixels * 0.7);
//        int height = (int) (metrics.heightPixels * 0.7);
//        getWindow().setLayout(width , height);
//
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.x = 0;
//        params.y = -20;
//        getWindow().setAttributes(params);

        save_exercise_button.setOnClickListener(v -> {
            dialog.show();
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        bodyPart = getIntent().getStringExtra("bodyPart").toString().trim();
        switch (bodyPart){
            case "LEGS":
                 myAdapter  = new ArrayAdapter<>(Add_routine_popUp_Activity.this ,
                        android.R.layout.simple_list_item_1,
                        legs_exercises);
                break;

            case "CHEST":
                 myAdapter  = new ArrayAdapter<>(Add_routine_popUp_Activity.this ,
                        android.R.layout.simple_list_item_1,
                        chest_exercises);
                break;

            case "SHOULDER":
                myAdapter  = new ArrayAdapter<>(Add_routine_popUp_Activity.this ,
                        android.R.layout.simple_list_item_1,
                        shoulder_exercises);
                break;

            case "BACK":
                myAdapter  = new ArrayAdapter<>(Add_routine_popUp_Activity.this ,
                        android.R.layout.simple_list_item_1,
                        back_exercises);
                break;
        }


        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exercise_spinner.setAdapter(myAdapter);
        exercise_spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        exercise_name = parent.getSelectedItem().toString().trim();
        counter = 1;
        set_number_textview.setText("SET 1");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void save_set(View view) {
        String reps = String.valueOf(reps_et.getText());
        String weight = String.valueOf(weight_et.getText());
        save_set_progressbar.setVisibility(View.VISIBLE);
//
//        if(!TextUtils.isEmpty(reps) && !TextUtils.isEmpty(weight)){
//            save_set_progressbar.setVisibility(View.VISIBLE);
//            Exercise_Set exerciseSet = new Exercise_Set();
//            exerciseSet.setUsername(UserApi.getInstance().getUsername());
//            exerciseSet.setUserID(UserApi.getInstance().getUserID());
//            exerciseSet.setReps(reps);
//            exerciseSet.setWeight(weight);
//            exerciseSet.setExercise(exercise_name);
//            exerciseSet.setCurrent_date(new Date());
//
//            collectionReference.document(bodyPart)
//                    .collection(exercise_name).document()
//                    .set(exerciseSet).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void unused) {
//                    counter++;
//                    set_number_textview.setText("SET " + counter);
//                    reps_et.setText("");
//                    weight_et.setText("");
//                    save_set_progressbar.setVisibility(View.GONE);
//                    Toast.makeText(Add_routine_popUp_Activity.this,"ADDED" , Toast.LENGTH_SHORT).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d("addingExercise", "onFailure: "+ e.toString());
//                    save_set_progressbar.setVisibility(View.GONE);
//                }
//            });
//
//        }else{
//            Toast.makeText(Add_routine_popUp_Activity.this , "ENTER ALL FIELDS", Toast.LENGTH_SHORT).show();
//        }



        if(!TextUtils.isEmpty(reps) && !TextUtils.isEmpty(weight)){
            save_set_progressbar.setVisibility(View.VISIBLE);
            Exercise_Set exerciseSet = new Exercise_Set();
            exerciseSet.setUsername(UserApi.getInstance().getUsername());
            exerciseSet.setUserID(UserApi.getInstance().getUserID());
            exerciseSet.setReps(reps);
            exerciseSet.setWeight(weight);
            exerciseSet.setExercise(exercise_name);
            exerciseSet.setCurrent_date(new Date());
            exerciseSet.setSet_number(counter);

            db.collection(exercise_name).document(String.valueOf(Timestamp.now().getSeconds())).set(exerciseSet)
             .addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void unused) {
                     counter++;
                     set_number_textview.setText("SET " + counter);
                     reps_et.setText("");
                     weight_et.setText("");
                     save_set_progressbar.setVisibility(View.GONE);
                     Toast.makeText(Add_routine_popUp_Activity.this,"ADDED" , Toast.LENGTH_SHORT).show();
                 }
             })
                    .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("addingExercise", "onFailure: "+ e.toString());
                    save_set_progressbar.setVisibility(View.GONE);
                }
            });
        }else{
            save_set_progressbar.setVisibility(View.GONE);
            Toast.makeText(Add_routine_popUp_Activity.this , "ENTER ALL FIELDS", Toast.LENGTH_SHORT).show();
        }


    }
}