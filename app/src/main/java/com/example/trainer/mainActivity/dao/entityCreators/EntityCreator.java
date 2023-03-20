package com.example.trainer.mainActivity.dao.entityCreators;

import android.database.Cursor;

public interface EntityCreator<E> {

    E createFrom(Cursor cursor);

}
