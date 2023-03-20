package com.example.trainer.mainActivity.dao.framework;

import com.example.trainer.schemas.User;

public interface IUserDAO {

    void createUser(User user);

    User getUser();

}
