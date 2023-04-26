package com.example.trainer.UI.users.userSearch;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.UI.UpdatableAdapter;
import com.example.trainer.UI.workouts.workoutHistory.WorkoutHistoryAdapter;
import com.example.trainer.model.User;
import com.example.trainer.model.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserSearchAdapter extends UpdatableAdapter<List<String>, UserSearchAdapter.ViewHolder> {

    private List<String> listOfUsers;

    public UserSearchAdapter() {
        this.listOfUsers = new ArrayList<>();
    }

    public void setListOfUsers(List<String> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    @Override
    public void update(List<String> data) {
        listOfUsers = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_user_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSearchAdapter.ViewHolder holder, int position) {
        holder.username.setText(listOfUsers.get(position));

    }

    @Override
    public int getItemCount() {
        return listOfUsers.size();
    }
}
