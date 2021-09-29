package com.example.trackit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackit.Util.Exercise_Set;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.trackit.Util.util.FormatDate_forChart;

public class BarChart_Activity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String exercise_name ;
    String currentUserID;
     List<Exercise_Set> exercise_setList = new ArrayList<>();
    List<Exercise_Set> exercise_set1 = new ArrayList<>();
    List<Exercise_Set> exercise_set2 = new ArrayList<>();
    List<Exercise_Set> exercise_set3 = new ArrayList<>();
    List<Exercise_Set> exercise_set4 = new ArrayList<>();
    List<Exercise_Set> exercise_set5 = new ArrayList<>();

    ArrayList barEntriesSet1 = new ArrayList();
    ArrayList barEntriesSet2 = new ArrayList();
    ArrayList barEntriesSet3 = new ArrayList();
    ArrayList barEntriesSet4 = new ArrayList();
    ArrayList barEntriesSet5 = new ArrayList();

    private TextView textView ;
    BarChart chartSet1 ,chartSet2 ,chartSet3 , chartSet4 ,chartSet5 ;
    BarData barData1 , barData2 ,barData3,barData4,barData5;
    BarDataSet barDataSet1, barDataSet2, barDataSet3, barDataSet4, barDataSet5 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        exercise_name = getIntent().getStringExtra("exerciseName");
        currentUserID = currentUser.getUid();
        textView = findViewById(R.id.textView15);
        chartSet1 = findViewById(R.id.set1_chart);
        chartSet2 = findViewById(R.id.set2_chart);
        chartSet3 = findViewById(R.id.set3_chart);
        chartSet4 = findViewById(R.id.set4_chart);
        chartSet5 = findViewById(R.id.set5_chart);

    //    Toast.makeText(BarChart_Activity.this , "CLICK ON THE SCREEN TO SEE GRAPH" ,Toast.LENGTH_SHORT).show();
//





        db.collection(exercise_name).whereEqualTo("userID" , currentUserID).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                                Exercise_Set exerciseSet = snapshot.toObject(Exercise_Set.class);
                                exercise_setList.add(exerciseSet);
                            }


                        }
                        if(exercise_setList.size()>0) {

                          // Toast.makeText(BarChart_Activity.this , "CLICK ON THE SCREEN TO SEE GRAPH" ,Toast.LENGTH_LONG).show();

                            for (Exercise_Set exerciseSet : exercise_setList) {
                                if(exerciseSet.getSet_number() == 1){
                                    exercise_set1.add(exerciseSet); }
                            }

                            for (Exercise_Set exerciseSet2 : exercise_setList) {
                                if(exerciseSet2.getSet_number() == 2){
                                    exercise_set2.add(exerciseSet2); }
                            }

                            for (Exercise_Set exerciseSet3 : exercise_setList) {
                                if(exerciseSet3.getSet_number() == 3){
                                    exercise_set3.add(exerciseSet3); }
                            }

                            for (Exercise_Set exerciseSet4 : exercise_setList) {
                                if(exerciseSet4.getSet_number() == 4){
                                    exercise_set4.add(exerciseSet4); }
                            }

                            for (Exercise_Set exerciseSet5 : exercise_setList) {
                                if(exerciseSet5.getSet_number() == 5){
                                    exercise_set5.add(exerciseSet5); }
                            }
                        }else{
                            textView.setText("No Data To Show!");
                        }
                        Collections.reverse(exercise_set1);
                        Collections.reverse(exercise_set2);
                        Collections.reverse(exercise_set3);
                        Collections.reverse(exercise_set4);
                        Collections.reverse(exercise_set5);

                        //Toast.makeText(BarChart_Activity.this , "Size:"  + exercise_setList.size() ,Toast.LENGTH_SHORT).show();

                        int counter1 =  0;
                        for(Exercise_Set exerciseSet :  exercise_set1){
                            barEntriesSet1.add(new BarEntry((float)counter1 , (int) Integer.parseInt(exerciseSet.getReps())));
                            counter1++;
                        }
                        int counter2 =  0;
                        for(Exercise_Set exerciseSet :  exercise_set2){
                            barEntriesSet2.add(new BarEntry((float)counter2 , (int) Integer.parseInt(exerciseSet.getReps())));
                            counter2++;
                        }
                        int counter3 =  0;
                        for(Exercise_Set exerciseSet :  exercise_set3){
                            barEntriesSet3.add(new BarEntry((float)counter3 , (int) Integer.parseInt(exerciseSet.getReps())));
                            counter1++;
                        }
                        int counter4 =  0;
                        for(Exercise_Set exerciseSet :  exercise_set4){
                            barEntriesSet4.add(new BarEntry((float)counter4 , (int) Integer.parseInt(exerciseSet.getReps())));
                            counter4++;
                        }
                        int counter5 =  0;
                        for(Exercise_Set exerciseSet :  exercise_set5){
                            barEntriesSet5.add(new BarEntry((float)counter5 , (int) Integer.parseInt(exerciseSet.getReps())));
                            counter5++;
                        }


                        if(exercise_set1.size()==0){
                            chartSet1.setVisibility(View.GONE);
                        }
                        if(exercise_set2.size()==0){
                            chartSet2.setVisibility(View.GONE);
                        }
                        if(exercise_set3.size()==0){
                            chartSet3.setVisibility(View.GONE);
                        }
                        if(exercise_set4.size()==0){
                            chartSet4.setVisibility(View.GONE);
                        }
                        if(exercise_set5.size()==0){
                            chartSet5.setVisibility(View.GONE);
                        }

                        String xAxisLabel1[] = new String[exercise_set1.size()];
                        for (int i = 0; i < exercise_set1.size(); i++) {
                            xAxisLabel1[i] = FormatDate_forChart(exercise_set1.get(i).getCurrent_date());
                        }
                        chartSet1.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel1));

                        String xAxisLabel2[] = new String[exercise_set2.size()];
                        for (int i = 0; i < exercise_set2.size(); i++) {
                            xAxisLabel2[i] = FormatDate_forChart(exercise_set2.get(i).getCurrent_date());
                        }
                        chartSet2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel2));

                        String xAxisLabel3[] = new String[exercise_set3.size()];
                        for (int i = 0; i < exercise_set3.size(); i++) {
                            xAxisLabel3[i] = FormatDate_forChart(exercise_set3.get(i).getCurrent_date());
                        }
                        chartSet3.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel3));

                        String xAxisLabel4[] = new String[exercise_set4.size()];
                        for (int i = 0; i < exercise_set4.size(); i++) {
                            xAxisLabel4[i] = FormatDate_forChart(exercise_set4.get(i).getCurrent_date());
                        }
                        chartSet4.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel4));

                        String xAxisLabel5[] = new String[exercise_set5.size()];
                        for (int i = 0; i < exercise_set5.size(); i++) {
                            xAxisLabel5[i] = FormatDate_forChart(exercise_set5.get(i).getCurrent_date());
                        }
                        chartSet5.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel5));


                        barDataSet1 = new BarDataSet(barEntriesSet1, "SET 1");
                        barDataSet2 = new BarDataSet(barEntriesSet2, "SET 2");
                        barDataSet3 = new BarDataSet(barEntriesSet3, "SET 3");
                        barDataSet4 = new BarDataSet(barEntriesSet4, "SET 4");
                        barDataSet5 = new BarDataSet(barEntriesSet5, "SET 5");

                        
                        barData1 = new BarData(barDataSet1);
                        barData2 = new BarData(barDataSet2);
                        barData3 = new BarData(barDataSet3);
                        barData4 = new BarData(barDataSet4);
                        barData5 = new BarData(barDataSet5);


                        chartSet1.setData(barData1);
                        chartSet2.setData(barData2);
                        chartSet3.setData(barData3);
                        chartSet4.setData(barData4);
                        chartSet5.setData(barData5);


                        barDataSet1.setValueTextColor(Color.BLACK);

                        barDataSet1.setValueTextSize(16f);
                        barDataSet2.setValueTextSize(16f);
                        barDataSet3.setValueTextSize(16f);
                        barDataSet4.setValueTextSize(16f);
                        barDataSet5.setValueTextSize(16f);

                        chartSet1.getDescription().setTextSize(20f);

                        chartSet1.getDescription().setEnabled(false);
                        chartSet2.getDescription().setEnabled(false);
                        chartSet3.getDescription().setEnabled(false);
                        chartSet4.getDescription().setEnabled(false);
                        chartSet5.getDescription().setEnabled(false);

                        chartSet1.invalidate();
                        chartSet2.invalidate();
                        chartSet3.invalidate();
                        chartSet4.invalidate();
                        chartSet5.invalidate();



                        exercise_setList.removeAll(exercise_setList);



                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("showData_adapter", "onFailure: "+ e.toString());
            }
        });



    }


}