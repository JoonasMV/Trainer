package com.example.trainer.ui.users.userSearch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.trainer.R;
import com.example.trainer.controllers.BaseController;

import java.util.List;
import java.util.stream.Collectors;

public class User_search_fragment extends Fragment {

    private List<String> usernames;

    public User_search_fragment() {
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

        // Creation of username list recyclerView
        RecyclerView listOfUsers = view.findViewById(R.id.userNameList);
        UserSearchAdapter adapter = new UserSearchAdapter();
        handleUserFetching(adapter);
        listOfUsers.setAdapter(adapter);
        listOfUsers.setLayoutManager(new LinearLayoutManager(getContext()));

        EditText usernameSearch = view.findViewById(R.id.editTextTextPersonName);
        usernameSearch.addTextChangedListener(new UsernameTextWatcher() {
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String filter = usernameSearch.getText().toString();
                List<String> filteredUsers;
                filteredUsers = usernames
                        .stream()
                        .filter(username -> username.contains(filter))
                        .collect(Collectors.toList());

                adapter.update(filteredUsers);
            }
        });
    }

    /**
     * Fetches the usernames from the database and updates the adapter
     * @param adapter the adapter to update
     */
    private void handleUserFetching(UserSearchAdapter adapter){
        new Thread(() -> {
            usernames = BaseController.getController().getUsernames();
            FragmentActivity activity = getActivity();
            if (activity == null) return;
            activity.runOnUiThread(UIRunnable(adapter, usernames));
        }).start();

    }

    /**
     * Creates a runnable that updates the adapter and hides the progress bar
     * @param adapter the adapter to update
     * @param usernames the usernames to update the adapter with
     * @return the runnable
     */
    private Runnable UIRunnable(UserSearchAdapter adapter, List<String> usernames){
        return () -> {
            adapter.update(usernames);
        };
    }
}