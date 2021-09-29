package com.example.trackit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class List_of_BodyPart_Activity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView back_textview , chest_textview , legs_textview , shoulder_textview;
    private ImageView back_imageview , chest_imageview , legs_imageview , shoulder_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bodypart);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        back_textview = findViewById(R.id.back_list_textview);
        chest_textview = findViewById(R.id.chest_list_textview);
        legs_textview = findViewById(R.id.legs_list_textview);
        shoulder_textview = findViewById(R.id.shoulder_list_textview);
        back_imageview = findViewById(R.id.profile_image_back);
        chest_imageview = findViewById(R.id.profile_image_chest);
        legs_imageview = findViewById(R.id.profile_image_legs);
        shoulder_imageview = findViewById(R.id.profile_image_shoulder);

         back_textview.setOnClickListener(v -> {
             Intent intent = new Intent(List_of_BodyPart_Activity.this , Exercises_bodyPart_Activity.class);
             intent.putExtra("bodyPart" , "BACK");
             startActivity(intent);
         });

         chest_textview.setOnClickListener(v -> {
             Intent intent = new Intent(List_of_BodyPart_Activity.this , Exercises_bodyPart_Activity.class);
             intent.putExtra("bodyPart" , "CHEST");
             startActivity(intent);
         });

        legs_textview.setOnClickListener(v -> {
            Intent intent = new Intent(List_of_BodyPart_Activity.this , Exercises_bodyPart_Activity.class);
            intent.putExtra("bodyPart" , "LEGS");
            startActivity(intent);
        });

        shoulder_textview.setOnClickListener(v -> {
            Intent intent = new Intent(List_of_BodyPart_Activity.this , Exercises_bodyPart_Activity.class);
            intent.putExtra("bodyPart" , "SHOULDER");
            startActivity(intent);
        });


    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_adding_sets, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
//            case R.id.add_new_set:
//               Intent intent =  new Intent(List_of_BodyPart_Activity.this, Add_routine_popUp_Activity.class);
//                intent.putExtra("bodyPart" , getIntent().getStringExtra("bodyPart").toString().trim());
//                startActivity(intent);
//                break;

            case R.id.sign_out_menu:
                if(currentUser!=null&& firebaseAuth!=null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(List_of_BodyPart_Activity.this , MainActivity.class));
                    finish();
                }
        }

        return super.onOptionsItemSelected(item);
    }
}