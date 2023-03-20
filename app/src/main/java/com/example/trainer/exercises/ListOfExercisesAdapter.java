package com.example.trainer.exercises;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.database.dao.ExerciseDAO;
import com.example.trainer.database.schemas.ExerciseType;
import com.example.trainer.workouts.mainActivity.MainActivity;

import java.util.List;

public class ListOfExercisesAdapter extends RecyclerView.Adapter<ListOfExercisesAdapter.ViewHolder> {

    private List<ExerciseType> exerciseTypeList;
    private ExerciseDAO exerciseDAO;

    public ListOfExercisesAdapter(List<ExerciseType> exerciseList) {
        this.exerciseTypeList = exerciseList;
        this.exerciseDAO = new ExerciseDAO();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameOfExercise;
        private Button deleteExerciseBtn;

        public ViewHolder(@NonNull View view) {
            super(view);
            nameOfExercise = view.findViewById(R.id.nameOfExercise);
            deleteExerciseBtn = view.findViewById(R.id.deleteExerciseBtn);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameOfExercise.setText(exerciseTypeList.get(position).getName());

        holder.deleteExerciseBtn.setOnClickListener(view -> {
            exerciseDAO.deleteExerciseTypeById(exerciseTypeList.get(position).getId());
            exerciseTypeList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ExerciseChart exerciseChart = new ExerciseChart();
                ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainContainer, exerciseChart,"")
                        .addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseTypeList.size();
    }
}
