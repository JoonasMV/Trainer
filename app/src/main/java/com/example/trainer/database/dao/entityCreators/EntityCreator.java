package com.example.trainer.database.dao.entityCreators;

import android.database.Cursor;

public interface EntityCreator<E> {

    E createFrom(Cursor cursor);

}
