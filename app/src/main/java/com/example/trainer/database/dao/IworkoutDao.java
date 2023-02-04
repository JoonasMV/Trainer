package com.example.trainer.database.dao;

import com.example.trainer.database.schemas.Exercise;
import com.example.trainer.database.schemas.Workout;

import java.util.ArrayList;
import java.util.List;

public interface IworkoutDao {

    public ArrayList<Workout> getAll();

    public Workout getById(int id);

    public int add(Workout workout);

    public int addMany(List<Workout> workouts);
}
