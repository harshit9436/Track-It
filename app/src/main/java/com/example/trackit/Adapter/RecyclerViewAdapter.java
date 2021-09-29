package com.example.trackit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackit.R;
import com.example.trackit.Util.Exercise_Set;

import java.util.List;
import java.util.zip.Inflater;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<String> exercise_list;
    private String bodyPart;
    onExerciseClickListener onExerciseClickListener;

    public RecyclerViewAdapter( List<String> exercise_list, String bodyPart , onExerciseClickListener onExerciseClickListener ) {

        this.exercise_list = exercise_list;
        this.bodyPart = bodyPart;
        this.onExerciseClickListener = onExerciseClickListener;
    }

    public RecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.exercises_row_recyclerview, parent, false);
        return  new ViewHolder(view, onExerciseClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        String bodyPart_final = bodyPart.toLowerCase().trim();
        holder.exerciseName_textview_row.setText(exercise_list.get(position));
        switch (bodyPart){
            case "CHEST":
                holder.exercise_imageview_row.setImageResource(R.drawable.chest);
                break;

            case "BACK":
                holder.exercise_imageview_row.setImageResource(R.drawable.back);
                break;

            case "LEGS":
                holder.exercise_imageview_row.setImageResource(R.drawable.legs);
                break;

            case "SHOULDER":
                holder.exercise_imageview_row.setImageResource(R.drawable.shoulder);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return exercise_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView exercise_imageview_row;
        private TextView exerciseName_textview_row;
        private CardView cardView_row;
        onExerciseClickListener onExerciseClickListener;

        public ViewHolder(@NonNull View itemView , onExerciseClickListener onExerciseClickListener) {
            super(itemView);
            this.onExerciseClickListener = onExerciseClickListener;
            itemView.setOnClickListener(this);
            exercise_imageview_row = itemView.findViewById(R.id.imageView_row);
            exerciseName_textview_row = itemView.findViewById(R.id.exercise_name_row);
            cardView_row = itemView.findViewById(R.id.cardview_row);


        }

        @Override
        public void onClick(View v) {
            String exercise_name = exercise_list.get(getAdapterPosition());
            if (v.getId() == exerciseName_textview_row.getId()) {
                    onExerciseClickListener.onImageViewClick();
            } else {
                onExerciseClickListener.onExerciseClick(exercise_name);

            }
        }
//
//        @Override
//        public void onClick(View v) {
//            String exercise_name = exercise_list.get(getAdapterPosition());
//        onExerciseClickListener.onExerciseClick(exercise_name);
//
//        }
    }

    public interface onExerciseClickListener {
         void onExerciseClick(String exercise_name);
         void onImageViewClick();
    }
}
