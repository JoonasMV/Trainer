package com.example.trainer.UI.users.userSearch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;
import com.example.trainer.controllers.TrainerController;
import com.example.trainer.model.User;

import java.util.List;

public class User_seach_fragment extends Fragment {

    public User_seach_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: Remove if not needed
//        if (container != null) {
//            container.removeAllViews();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_seach_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        TrainerController controller = BaseController.getController();
        List<User> userList = controller.findAllUsers();

        // Creation of username list recyclerView
        RecyclerView listOfUsers = view.findViewById(R.id.userNameList);
        UserSearchAdapter adapter = new UserSearchAdapter(userList);
        listOfUsers.setAdapter(adapter);
        listOfUsers.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}