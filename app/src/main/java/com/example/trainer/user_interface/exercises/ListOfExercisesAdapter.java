package com.example.trainer.user_interface.exercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.schemas.ExerciseType;

import java.util.List;

public class ListOfExercisesAdapter extends RecyclerView.Adapter<ListOfExercisesAdapter.ViewHolder> {

    private List<ExerciseType> exerciseTypes;

    public ListOfExercisesAdapter() {
        exerciseTypes = BaseController.getController().getExerciseTypes();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameOfExercise;
        private final PopupMenu ppMenu;

        public ViewHolder(@NonNull View view) {
            super(view);
            nameOfExercise = view.findViewById(R.id.nameOfExercise);
            ppMenu = new PopupMenu(view.getContext(), nameOfExercise);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        exerciseTypes = BaseController.getController().getExerciseTypes();
        System.out.println(exerciseTypes.size());
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameOfExercise.setText(exerciseTypes.get(position).getName());

        holder.nameOfExercise.setOnLongClickListener(view -> {
        holder.ppMenu.getMenuInflater().inflate(R.menu.exercise_popup_menu, holder.ppMenu.getMenu());
        holder.ppMenu.setOnMenuItemClickListener(menuItem -> {
            int hPosition = holder.getLayoutPosition();
            deleteExerciseType(hPosition);
            return false;
        });
        holder.ppMenu.show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return exerciseTypes.size();
    }

    private void deleteExerciseType(int position) {
        BaseController.getController().deleteExerciseType(exerciseTypes.get(position).getId());
        exerciseTypes.remove(position);
        //TODO: change notifItemRangeChanged
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}


