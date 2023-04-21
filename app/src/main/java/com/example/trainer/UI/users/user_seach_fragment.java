package com.example.trainer.UI.users;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user_seach_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_seach_fragment extends Fragment {

    public user_seach_fragment() {
        // Required empty public constructor
    }
    public static user_seach_fragment newInstance(String param1, String param2) {
        user_seach_fragment fragment = new user_seach_fragment();
        return fragment;
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
        return inflater.inflate(R.layout.fragment_user_seach_fragment, container, false);
    }
}