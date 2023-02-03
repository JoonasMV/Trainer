package com.example.trainer.workouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.trainer.MainActivity;
import com.example.trainer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListOfWorkouts_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOfWorkouts_fragment extends Fragment {

    private Button newWorkoutBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListOfWorkouts_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListOfWorkouts_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListOfWorkouts_fragment newInstance(String param1, String param2) {
        ListOfWorkouts_fragment fragment = new ListOfWorkouts_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_of_workouts_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        newWorkoutBtn = view.findViewById(R.id.newWorkoutBtn);

        Context context = getContext();

        newWorkoutBtn.setOnClickListener(viewa -> {
            startActivity(new Intent(context, CreateWorkoutActivity.class));
        });

    }
}