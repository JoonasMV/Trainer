package com.example.trainer.database.dao;

import com.example.trainer.database.schemas.Exercise;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface IexerciseDao {

    public ArrayList<Exercise> getAllExercises();

    public Exercise getExerciseById(int id);

    public int addExercise(Exercise exercise);

    public int addManyExercises(ArrayList<Exercise> exercises);


}
