package com.example.trainer.dao.sqlite;

import android.content.ContentValues;

import com.example.trainer.dao.entityCreators.UserEntityCreator;
import com.example.trainer.dao.framework.IUserDAO;
import com.example.trainer.database.contracts.UserContract;
import com.example.trainer.dao.framework.DAOBase;
import com.example.trainer.schemas.User;

import java.util.List;

public class SqliteUserDao extends DAOBase<User> implements IUserDAO {

    SqliteUserDao() {
        super(new UserEntityCreator(), UserContract.UserEntry.TABLE_USER);
    }

    @Override
    public void createUser(User user) {
        ContentValues cv = new ContentValues();
        cv.put(UserContract.UserEntry.USERNAME, user.getUsername());
        saveToDb(cv);
    }

    @Override
    public User getUser() {
        List<User> result = selectFromDb(null, null);
        return result.isEmpty() ? null : result.get(0);
    }
}
