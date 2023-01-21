package com.example.trainer.database.dao;

import com.example.trainer.database.schemas.Exercise;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface IexerciseDao {

    public List<Exercise> getAllExercises();

    void addExercise(String newExercise);

    public Exercise getExerciseById(int id);

    public int addManyExercises(List<Exercise> exercises);


}
