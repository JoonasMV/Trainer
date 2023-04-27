package com.example.trainer.ui.users.userSearch;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainer.R;
import com.example.trainer.ui.UpdatableAdapter;
import com.example.trainer.ui.MainActivity;
import com.example.trainer.ui.UserProfile_fragment;

import java.util.ArrayList;
import java.util.List;

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
        String name = listOfUsers.get(position);
        holder.username.setText(name);

        holder.username.setOnClickListener(v -> {
            ((MainActivity) v.getContext()).getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.mainContainer, UserProfile_fragment.newInstance(name))
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return listOfUsers.size();
    }
}
