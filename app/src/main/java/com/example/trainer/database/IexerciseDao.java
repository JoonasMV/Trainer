package com.example.trainer.database;

public interface IexerciseDao {

    public String[] getAllExercises();

    public Exercise getExerciseById();

    public int addExercise();

    public int addManyExercises();


}
