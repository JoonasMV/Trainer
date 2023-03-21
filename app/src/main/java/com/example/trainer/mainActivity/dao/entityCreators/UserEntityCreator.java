package com.example.trainer.mainActivity.dao.entityCreators;

import android.database.Cursor;

import com.example.trainer.database.contracts.UserContract;
import com.example.trainer.schemas.User;

public class UserEntityCreator implements EntityCreator<User>{

    public UserEntityCreator(){

    }

    @Override
    public User createFrom(Cursor cursor) {
        String username = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.USERNAME));

        if(username == null){
            throw new NullPointerException("username == null when reading cursor");
        }

        return new User(username);
    }
}
