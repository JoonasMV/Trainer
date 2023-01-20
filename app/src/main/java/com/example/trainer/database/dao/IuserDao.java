package com.example.trainer.database.dao;

import com.example.trainer.database.schemas.User;

public interface IuserDao {

    public int addUser(User user);

    public User getUser(int id);

}
