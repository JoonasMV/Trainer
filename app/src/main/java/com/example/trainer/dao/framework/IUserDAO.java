package com.example.trainer.dao.framework;

import com.example.trainer.schemas.User;

public interface IUserDAO {

    void createUser(User user);

    User getUser();

}
