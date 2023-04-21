package com.example.trainer.UI.exercises.exerciseList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.UI.MainActivity;
import com.example.trainer.R;
import com.example.trainer.UI.exercises.exerciseChart.ExerciseChart_fragment;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.model.ExerciseType;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder> {

    private List<ExerciseType> exerciseTypes;
    private PopupMenu ppMenu;

    public ExerciseListAdapter(List<ExerciseType> types) {
        this.exerciseTypes = types;
        Log.d("types", Integer.toString(exerciseTypes.size()));

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameOfExercise;

        private final ImageButton threeDots;

        public ViewHolder(@NonNull View view) {
            super(view);
            nameOfExercise = view.findViewById(R.id.typeName);
            threeDots = view.findViewById(R.id.threedots_button);


        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("onCreate", "jee");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.nameOfExercise.setText(exerciseTypes.get(position).getName());

        // Short click to view diagram
        holder.nameOfExercise.setOnClickListener(view -> {
            System.out.println("short click");
            Bundle args = new Bundle();
            args.putSerializable(null, exerciseTypes.get(holder.getAdapterPosition()));
            ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer, ExerciseChart_fragment.newInstance(exerciseTypes.get(holder.getAdapterPosition())),null)
                    .addToBackStack(null).commit();

        });

        // long click to open menu
        holder.threeDots.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View view) {
                ppMenu = new PopupMenu(view.getContext(), holder.threeDots );
                ppMenu.getMenuInflater().inflate(R.menu.exercise_popup_menu, ppMenu.getMenu());
                ppMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.show_chart:
                            Bundle args = new Bundle();
                            args.putSerializable(null, exerciseTypes.get(holder.getAdapterPosition()));
                            ((MainActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.mainContainer, ExerciseChart_fragment.newInstance(exerciseTypes.get(holder.getAdapterPosition())), null)
                                    .addToBackStack(null).commit();
                            break;
                        case R.id.delete_exercise:
                            deleteExerciseType(holder.getLayoutPosition());
                            break;
                    }
                    return true;
                });
                ppMenu.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return exerciseTypes.size();
    }

    private void deleteExerciseType(int position) {
        BaseController.getController().deleteExerciseType(exerciseTypes.get(position).getId());
        exerciseTypes.remove(position);
        notifyItemRemoved(position);
    }
}


