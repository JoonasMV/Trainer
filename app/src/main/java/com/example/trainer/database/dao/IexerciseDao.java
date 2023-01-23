package com.example.trainer.database.dao;

import com.example.trainer.database.schemas.Exercise;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface IexerciseDao {

    Exercise getExercise(String exerciseToQuery);

    public List<Exercise> getAllExercises();

    boolean addExercise(String newExercise);

    public int addManyExercises(List<Exercise> exercises);


}
