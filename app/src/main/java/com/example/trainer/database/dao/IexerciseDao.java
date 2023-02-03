package com.example.trainer.database.dao;

import com.example.trainer.database.schemas.Exercise;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface IexerciseDao {


    public List<Exercise> getAllExercises();


    public int addManyExercises(List<Exercise> exercises);


}
