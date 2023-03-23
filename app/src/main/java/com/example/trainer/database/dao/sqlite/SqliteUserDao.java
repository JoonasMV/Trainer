package com.example.trainer.database.dao.sqlite;

import android.content.ContentValues;

import com.example.trainer.database.dao.entityCreators.UserEntityCreator;
import com.example.trainer.database.dao.framework.IUserDAO;
import com.example.trainer.database.contracts.UserContract;
import com.example.trainer.database.dao.framework.DAOBase;
import com.example.trainer.schemas.User;
import com.example.trainer.serverConnector.Server;

import java.util.List;

public class SqliteUserDao extends DAOBase<User> implements IUserDAO {

    SqliteUserDao() {
        super(new UserEntityCreator(), UserContract.UserEntry.TABLE_USER);
    }

    @Override
    public void createUser(User user) {
        ContentValues cv = new ContentValues();

        User createdUser = Server.getInstance().user().save(user);
        cv.put(UserContract.UserEntry.USER_ID, createdUser.getId());
        cv.put(UserContract.UserEntry.USERNAME, createdUser.getUsername());
        saveToDb(cv);
    }

    @Override
    public User getUser() {
        List<User> result = selectFromDb(null, null);
        return result.isEmpty() ? null : result.get(0);
    }
}
