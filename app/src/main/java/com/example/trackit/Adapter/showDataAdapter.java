package com.example.trackit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackit.R;
import com.example.trackit.Util.Exercise_Set;
import com.example.trackit.Util.util;


import java.util.List;

public class showDataAdapter extends RecyclerView.Adapter<showDataAdapter.ViewHolder> {
    private Context context;
    private List<Exercise_Set> exercise_setList;

    public showDataAdapter(Context context, List<Exercise_Set> exercise_setList) {
        this.context = context;
        this.exercise_setList = exercise_setList;
    }

    public showDataAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showdata_recyclerview_row, parent ,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull showDataAdapter.ViewHolder holder, int position) {
    Exercise_Set exerciseSet = exercise_setList.get(position);
    holder.date_textview.setText(util.FormatDate(exerciseSet.getCurrent_date()));
    holder.reps_textview.setText(exerciseSet.getReps());
    holder.weight_textview.setText(exerciseSet.getWeight());
    holder.set_number_textview.setText(String.valueOf(exerciseSet.getSet_number()));
    }

    @Override
    public int getItemCount() {
        return exercise_setList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date_textview , reps_textview , weight_textview , set_number_textview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date_textview = itemView.findViewById(R.id.date_textview_row);
            reps_textview = itemView.findViewById(R.id.reps_textview_row);
            weight_textview = itemView.findViewById(R.id.weight_textview_row);
            set_number_textview = itemView.findViewById(R.id.set_no_textview_row);
        }
    }
}
